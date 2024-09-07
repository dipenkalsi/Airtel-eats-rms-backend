package com.airteleats.Airtel.eats.service;

import com.airteleats.Airtel.eats.model.IngredientCategory;
import com.airteleats.Airtel.eats.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, long categoryId) throws Exception;

    public List<IngredientsItem> findIngredientsByRestaurantId(Long id) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

}
