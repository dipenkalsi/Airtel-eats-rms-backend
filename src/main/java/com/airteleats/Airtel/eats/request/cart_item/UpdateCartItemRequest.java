package com.airteleats.Airtel.eats.request.cart_item;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long cartItemId;
    private int quantity;
}
