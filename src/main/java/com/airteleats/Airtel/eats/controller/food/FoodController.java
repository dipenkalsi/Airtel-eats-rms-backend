package com.airteleats.Airtel.eats.controller.food;

import com.airteleats.Airtel.eats.model.Food;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.request.CreateFoodRequest;
import com.airteleats.Airtel.eats.service.FoodService;
import com.airteleats.Airtel.eats.service.RestaurantService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> searchRestaurantFood(@RequestParam boolean isVeg,
                                                           @RequestParam boolean isNonVeg,
                                                           @RequestParam boolean isSeasonal,
                                                           @PathVariable Long restaurantId,
                                                           @PathVariable(required = false) String foodCategory,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, isVeg, isNonVeg, isSeasonal, foodCategory);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}
