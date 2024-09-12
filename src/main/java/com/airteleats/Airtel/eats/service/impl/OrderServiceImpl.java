package com.airteleats.Airtel.eats.service.impl;

import com.airteleats.Airtel.eats.model.*;
import com.airteleats.Airtel.eats.repository.AddressRepository;
import com.airteleats.Airtel.eats.repository.RestaurantRepository;
import com.airteleats.Airtel.eats.repository.UserRepository;
import com.airteleats.Airtel.eats.repository.order.OrderItemRepository;
import com.airteleats.Airtel.eats.repository.order.OrderRespository;
import com.airteleats.Airtel.eats.request.OrderRequest;
import com.airteleats.Airtel.eats.service.CartService;
import com.airteleats.Airtel.eats.service.OrderService;
import com.airteleats.Airtel.eats.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRespository orderRespository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;


    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception{
        Address shippingAddress = req.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);
        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        createdOrder.setItems(orderItems);
        Long totalPrice = cartService.calculateCartTotal(cart);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRespository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERED") || orderStatus.equals("DELIVERED") ||
        orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRespository.save(order);
        }
        else throw new Exception("Please choose a valid order status");
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRespository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRespository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders =  orderRespository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders = orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order= orderRespository.findById(orderId);
        if(order.isEmpty()){
            throw new Exception("Order with id="+orderId+" does not exist");
        }

        return order.get();
    }
}
