package com.airteleats.Airtel.eats.controller.restaurant;

import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.request.CreateRestaurantRequest;
import com.airteleats.Airtel.eats.response.MessageResponse;
import com.airteleats.Airtel.eats.service.RestaurantService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class RestaurantAdminController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if(!user.getRole().toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new Exception("Cannot create a restaurant as you are a customer. Create a restaurant owner account for creating a restaurant.");
        }
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if(!user.getRole().toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new Exception("Cannot update a restaurant as you are a customer. Create a restaurant owner account for creating a restaurant.");
        }
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if(!user.getRole().toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new Exception("Cannot update a restaurant status as you are a customer. Create a restaurant owner account for creating a restaurant.");
        }
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if(!user.getRole().toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new Exception("Cannot delete a restaurant as you are a customer. Create a restaurant owner account for creating a restaurant.");
        }
        restaurantService.deleteRestaurant(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted successfully.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        if(!user.getRole().toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new Exception("Cannot create a restaurant as you are a customer. Create a restaurant owner account for creating a restaurant.");
        }
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
