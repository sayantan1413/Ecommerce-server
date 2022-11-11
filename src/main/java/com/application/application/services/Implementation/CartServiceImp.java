package com.application.application.services.Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.application.dto.AddToCart;
import com.application.application.dto.CartDetailsDto;
import com.application.application.dto.CartItemsDto;
import com.application.application.model.Cart;
import com.application.application.model.Product;
import com.application.application.model.User;
import com.application.application.repository.CartDao;
import com.application.application.services.AuthenticationService;
import com.application.application.services.CartService;
import com.application.application.services.ProductService;

@Transactional
@Service
public class CartServiceImp implements CartService {

    @Autowired
    CartDao cartDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void addToCart(AddToCart addToCart, String email) {
        Product product = productService.getProductById(addToCart.getProductId());
        User user = authenticationService.authenticateUser(email);
        Cart cart = cartDao.findByUserAndProduct(user, product).orElse(null);
        int quatitity = cart == null ? 0 : cart.getQuantity();
        if (cart != null) {
            cart = updateCartItem(addToCart, cart);
        } else
            cart = new Cart(product, addToCart.getQuantity(), user);
        updateProductQauntity(product, quatitity, addToCart.getQuantity());
        saveCart(cart);
    }

    @Override
    public CartDetailsDto listCartItems(String email) {
        User user = authenticationService.authenticateUser(email);
        List<Cart> cartList = cartDao.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemsDto> cartItemsList = new ArrayList<>();
        if (cartList.isEmpty())
            return new CartDetailsDto();
        double totalPrice = 0;
        for (Cart cart : cartList) {
            totalPrice += cart.getProduct().getProductPrice();
            CartItemsDto cartItems = new CartItemsDto(cart);
            cartItemsList.add(cartItems);
        }
        return new CartDetailsDto(cartItemsList, totalPrice);
    }

    private void saveCart(Cart cart) {
        cartDao.save(cart);
    }

    private Cart updateCartItem(AddToCart addToCart, Cart cart) {
        cart.setQuantity(addToCart.getQuantity());
        cart.setCreatedDate(LocalDate.now());
        return cart;
    }

    private void updateProductQauntity(Product product, int cartItemsCount, int itemsCount) {
        product.setProductCount((product.getProductCount() - itemsCount + cartItemsCount));
        productService.updateQuatity(product);
    }

    @Override
    public void deleteCartItem(String email, long productId) {
        Product product = productService.getProductById(productId);
        User user = authenticationService.authenticateUser(email);
        Cart cart = cartDao.findByUserAndProduct(user, product).orElse(null);
        updateProductQauntity(product, cart.getQuantity(), 0);
        cartDao.deleteByProductAndUser(product, user);
    }

    @Override
    public void deleteCartItems(String email) {
        User user = authenticationService.authenticateUser(email);
        List<Cart> cartItems = cartDao.findAllByUser(user);
        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            updateProductQauntity(product, cartItem.getQuantity(), 0);
        }
        cartDao.deleteByUser(user);
    }

    @Override
    public void deleteCart(User user) {
        cartDao.deleteByUser(user);
    }
}