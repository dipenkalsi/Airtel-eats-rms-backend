package com.airteleats.Airtel.eats.repository;

import com.airteleats.Airtel.eats.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}
