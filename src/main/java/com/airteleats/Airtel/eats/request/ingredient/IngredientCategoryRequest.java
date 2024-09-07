package com.airteleats.Airtel.eats.request.ingredient;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private Long restaurantId;
}
