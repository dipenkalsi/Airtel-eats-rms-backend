package com.airteleats.Airtel.eats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String streetAddress;

    private String city;

    private String postalCode;

    private String country;

    @JsonIgnore
    @OneToOne(mappedBy = "address")  // `address` is the field name in the `Restaurant` entity
    private Restaurant restaurant;

}
