package com.airteleats.Airtel.eats.controller.order;

import com.airteleats.Airtel.eats.model.Order;
import com.airteleats.Airtel.eats.model.Restaurant;
import com.airteleats.Airtel.eats.model.User;
import com.airteleats.Airtel.eats.request.OrderRequest;
import com.airteleats.Airtel.eats.service.OrderService;
import com.airteleats.Airtel.eats.service.RestaurantService;
import com.airteleats.Airtel.eats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestParam(required = false) String order_status, @PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        List<Order> orders = orderService.getRestaurantsOrder(id, order_status);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,@PathVariable String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception{
//        Restaurant restaurant = restaurantService.findRestaurantById(id);
        Order order = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
