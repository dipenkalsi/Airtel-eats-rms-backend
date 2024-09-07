package com.airteleats.Airtel.eats.controller;

import com.airteleats.Airtel.eats.model.IngredientCategory;
import com.airteleats.Airtel.eats.model.IngredientsItem;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.request.ingredient.IngredientCategoryRequest;
import com.airteleats.Airtel.eats.request.ingredient.IngredientItemRequest;
import com.airteleats.Airtel.eats.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/admin/ingredients/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception{
            IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
            return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/admin/ingredients/item")
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientItemRequest req) throws Exception{
        IngredientsItem item = ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/admin/ingredients/item/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientItemStock(
            @PathVariable Long id) throws Exception{
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/ingredients/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredientItems(
            @PathVariable Long id) throws Exception{
        List<IngredientsItem> items = ingredientsService.findIngredientsByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/category/ingredients/category/restaurant/{id}")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientsCategory(
            @PathVariable Long id
    ) throws Exception{
        List<IngredientCategory> items = ingredientsService.findIngredientCategoriesByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
