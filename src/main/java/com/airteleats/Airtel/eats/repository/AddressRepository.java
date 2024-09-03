package com.airteleats.Airtel.eats.repository;

import com.airteleats.Airtel.eats.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {



}
