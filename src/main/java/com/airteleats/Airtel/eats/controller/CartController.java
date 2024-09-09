package com.airteleats.Airtel.eats.controller;

import com.airteleats.Airtel.eats.model.Cart;
import com.airteleats.Airtel.eats.model.CartItem;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.request.cart_item.AddCartItemRequest;
import com.airteleats.Airtel.eats.request.cart_item.UpdateCartItemRequest;
import com.airteleats.Airtel.eats.service.CartService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CartItem cartItem = cartService.addToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);

    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);

    }

    @DeleteMapping("/cart-item/{id}/delete")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable Long id,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

}
