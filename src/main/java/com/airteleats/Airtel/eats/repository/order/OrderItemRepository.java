package com.airteleats.Airtel.eats.repository.order;

import com.airteleats.Airtel.eats.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
