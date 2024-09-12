package com.airteleats.Airtel.eats.request;

import com.airteleats.Airtel.eats.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
