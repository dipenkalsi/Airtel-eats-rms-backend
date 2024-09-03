package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.dto.RestaurantDto;
import com.airteleats.Airtel.eats.model.Address;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.repository.AddressRepository;
import com.airteleats.Airtel.eats.repository.RestaurantRepository;
import com.airteleats.Airtel.eats.repository.UserRepository;
import com.airteleats.Airtel.eats.request.CreateRestaurantRequest;
import com.airteleats.Airtel.eats.service.RestaurantService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setOpen(true);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.deleteFromFavorites(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();

    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        //searches the keyword in the names and cuisine types of the restaurants.
        return restaurantRepository.findBySearchQuery(keyword);

    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Restaurant with this Id="+id+" does not exist");
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant =  restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("No restaurants owned by userid="+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourite(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto= new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFav = false;
        List<RestaurantDto> favs = user.getFavorites();
        for(RestaurantDto it: favs){
            if(it.getId().equals(restaurantId)){
                isFav = true;
                break;
            }
        }
        if(isFav){
            favs.removeIf(fav->fav.getId().equals(restaurantId));
        }
        else favs.add(restaurantDto);
        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
