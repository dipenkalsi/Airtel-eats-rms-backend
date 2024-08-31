package com.airteleats.Airtel.eats.model;

import com.airteleats.Airtel.eats.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    // whenever I want to fetch the users, I don't want to fetch their orders as well. I will create a separate API for the same. That is why I have used this annotation
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer") // One user can have multiple orders
    // mapped by Customer because inside Order Entity there will be a 'customer' field whose type will be user.
    // By giving mappedBy property, we are telling springboot to not create a seperate table for this mapping and instead just use the customer field in order table.
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Whenever we delete the user, all the addresses associated with this user will automatically be deleted from the Addresses table
    private List<Address> addresses = new ArrayList<>();
}
