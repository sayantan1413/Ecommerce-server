package com.application.application.services;

import java.util.List;

import com.application.application.dto.OrderDto;
import com.application.application.model.User;

public interface OrderService {

    public void placeOrder(User user) throws Exception;

    public List<OrderDto> getOrders(User user);

}
