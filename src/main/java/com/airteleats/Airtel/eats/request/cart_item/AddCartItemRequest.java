package com.airteleats.Airtel.eats.request.cart_item;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
