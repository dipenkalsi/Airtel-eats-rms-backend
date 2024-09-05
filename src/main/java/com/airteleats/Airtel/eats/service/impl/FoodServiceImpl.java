package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.model.Category;
import com.airteleats.Airtel.eats.model.Food;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.repository.FoodRepository;
import com.airteleats.Airtel.eats.request.CreateFoodRequest;
import com.airteleats.Airtel.eats.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food savedFoodItem = foodRepository.save(food);
        restaurant.getFoods().add(savedFoodItem);

        return savedFoodItem;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);

        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVeg, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVeg && !isNonVeg){
            foods = filterByVegetarian(foods);
        }
        if(isNonVeg && !isVeg){
            foods = filterByNonVegetarian(foods);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods);
        }
        if(foodCategory!=null && !foodCategory.equals("")){
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods) {
        return foods.stream().filter(food->food.isVegetarian()==true).collect(Collectors.toList());
    }
    private List<Food> filterByNonVegetarian(List<Food> foods) {
        return foods.stream().filter(food->food.isVegetarian()==false).collect(Collectors.toList());
    }
    private List<Food> filterBySeasonal(List<Food> foods){
        return foods.stream().filter(food->food.isSeasonal()==false).collect(Collectors.toList());
    }
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("Food item with id="+foodId+" does not exist.");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
