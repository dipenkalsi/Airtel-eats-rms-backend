package com.airteleats.Airtel.eats.service;

import com.airteleats.Airtel.eats.model.Cart;
import com.airteleats.Airtel.eats.model.CartItem;
import com.airteleats.Airtel.eats.request.cart_item.AddCartItemRequest;

public interface CartService {
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;

}
