package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.model.IngredientCategory;
import com.airteleats.Airtel.eats.model.IngredientsItem;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.repository.IngredientCategoryRepository;
import com.airteleats.Airtel.eats.repository.IngredientItemRepository;
import com.airteleats.Airtel.eats.service.IngredientsService;
import com.airteleats.Airtel.eats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setName(name);
        category.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Category with id = "+id+" does not exist");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
//        return List.of();
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredientsItem = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredientsItem);

        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findIngredientsByRestaurantId(Long id) throws Exception {
        return ingredientItemRepository.findByRestaurantId(id);
//        return List.of();
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient item with id="+id+" does not exist.");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
