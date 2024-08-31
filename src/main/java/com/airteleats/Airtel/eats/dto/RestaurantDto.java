package com.airteleats.Airtel.eats.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto { //DTO means Data transfer objects
    private String title;

    @Column(length=1000)
    private List<String> images;
    private String description;
    private Long id;

}
