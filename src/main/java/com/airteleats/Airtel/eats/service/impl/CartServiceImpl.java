package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.model.Cart;
import com.airteleats.Airtel.eats.model.CartItem;
import com.airteleats.Airtel.eats.model.Food;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.repository.CartItemRepository;
import com.airteleats.Airtel.eats.repository.CartRepository;
import com.airteleats.Airtel.eats.request.cart_item.AddCartItemRequest;
import com.airteleats.Airtel.eats.service.CartService;
import com.airteleats.Airtel.eats.service.FoodService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user  = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem: cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity()* food.getPrice());

        CartItem savedItem = cartItemRepository.save(cartItem);
        cart.getItems().add(savedItem);
        return savedItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("Cart with id="+cartItemId+" not found");
        }
        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice((cartItem.getFood().getPrice())*quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user  = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("Cart with id="+cartItemId+" not found");
        }

        CartItem item=cartItemOptional.get();
        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        long total = 0L;
        for(CartItem cartItem: cart.getItems()){
            Food food = cartItem.getFood();
            total+= food.getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("Cart with id="+id+" not found");
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);

        Cart cart =  cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);

        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
