package com.airteleats.Airtel.eats.service;

import com.airteleats.Airtel.eats.model.Category;
import com.airteleats.Airtel.eats.model.Food;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVeg, boolean isNonVeg,
                                         boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}