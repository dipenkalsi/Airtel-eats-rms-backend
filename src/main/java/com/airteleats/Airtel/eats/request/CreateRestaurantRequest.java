package com.airteleats.Airtel.eats.request;

import com.airteleats.Airtel.eats.model.Address;
import com.airteleats.Airtel.eats.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
