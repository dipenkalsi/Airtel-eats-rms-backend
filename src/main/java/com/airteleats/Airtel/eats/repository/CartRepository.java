package com.airteleats.Airtel.eats.repository;

import com.airteleats.Airtel.eats.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
