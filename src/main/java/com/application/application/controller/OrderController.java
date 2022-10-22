package com.application.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.application.dto.OrderDto;
import com.application.application.model.User;
import com.application.application.services.AuthenticationService;
import com.application.application.services.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestHeader("email") String email) {
        try {
            User user = authenticationService.authenticateUser(email);
            return ResponseEntity.ok().body(orderService.getOrders(user));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> placeOrder(@RequestHeader("email") String email) {
        try {
            User user = authenticationService.authenticateUser(email);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/order/add").toUriString());
            orderService.placeOrder(user);
            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
