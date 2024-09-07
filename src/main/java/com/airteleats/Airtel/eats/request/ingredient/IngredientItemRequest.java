package com.airteleats.Airtel.eats.request.ingredient;

import lombok.Data;

@Data
public class IngredientItemRequest {
    private String name;
    private Long categoryId;
    private Long restaurantId;

}
