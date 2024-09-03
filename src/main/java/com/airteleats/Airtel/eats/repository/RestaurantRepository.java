package com.airteleats.Airtel.eats.repository;

import com.airteleats.Airtel.eats.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(value = "SELECT r from Restaurant r where lower(r.name) like lower(concat('%', :query, '%')) or lower(r.cuisineType) like lower(concat('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(String query);

    @Modifying
    @Transactional
    @Query(value = "Delete from user_favorites where user_favorites.id=:id", nativeQuery = true)
    void deleteFromFavorites(@Param("id") Long id);

    Restaurant findByOwnerId(Long userId);

}
