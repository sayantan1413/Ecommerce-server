package com.application.application.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.application.dto.AddToCart;
import com.application.application.dto.CartDetailsDto;
import com.application.application.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCart addToCart,
            @RequestHeader("email") String email) {
        try {

            cartService.addToCart(addToCart, email);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cart/add").toUriString());
            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/list")
    public ResponseEntity<EntityModel<CartDetailsDto>> getCartItems(@RequestHeader("email") String email) {
        try {
            return new ResponseEntity<>(EntityModel.of(cartService.listCartItems(email)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("productId") long productId,
            @RequestHeader("email") String email) {
        try {

            cartService.deleteCartItem(email, productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCartItems(@RequestHeader("email") String email) {
        try {
            cartService.deleteCartItems(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }

    }

}
