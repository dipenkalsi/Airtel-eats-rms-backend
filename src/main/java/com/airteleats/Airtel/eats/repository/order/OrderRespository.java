package com.airteleats.Airtel.eats.repository.order;

import com.airteleats.Airtel.eats.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRespository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);

}
