package com.airteleats.Airtel.eats.service;

import com.airteleats.Airtel.eats.model.Order;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest req, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;


}
