package com.airteleats.Airtel.eats.repository;

import com.airteleats.Airtel.eats.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(value = "SELECT * from Restaurant where lower(r.name) like lower(concat('%', :query, '%')) or lower(r.cuisineType) like lower(concat('%', :query, '%'))", nativeQuery = true)
    List<Restaurant> findBySearchQuery(String query);
    Restaurant findByOwnerId(Long userId);

}
