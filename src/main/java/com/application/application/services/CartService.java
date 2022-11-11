package com.application.application.services;

import com.application.application.dto.AddToCart;
import com.application.application.dto.CartDetailsDto;
import com.application.application.model.User;

public interface CartService {
    public void addToCart(AddToCart addToCart, String email);

    public CartDetailsDto listCartItems(String email) throws Exception;

    public void deleteCartItem(String email, long productId);

    public void deleteCartItems(String email);

    public void deleteCart(User user);

}
