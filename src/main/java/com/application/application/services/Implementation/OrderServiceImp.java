package com.application.application.services.Implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.application.dto.CartDetailsDto;
import com.application.application.dto.CartItemsDto;
import com.application.application.dto.OrderDto;
import com.application.application.dto.OrderItemsDto;
import com.application.application.dto.ProductDto;
import com.application.application.model.Order;
import com.application.application.model.OrderItems;
import com.application.application.model.User;
import com.application.application.repository.OrderDao;
import com.application.application.repository.OrderItemsDao;
import com.application.application.services.CartService;
import com.application.application.services.IdGenrator;
import com.application.application.services.OrderService;
import com.application.application.services.ProductService;

@Transactional
@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemsDao orderItemsDao;

    @Override
    public void placeOrder(User user) throws Exception {
        CartDetailsDto cartDetails = cartService.listCartItems(user.getEmail());
        Order order = new Order(IdGenrator.generate(), user, cartDetails.getTotalCost());
        orderDao.save(order);
        for (CartItemsDto cartItems : cartDetails.getCartItems()) {
            orderItemsDao.save(new OrderItems(order,
                    productService.getProductById(cartItems.getProductDto().getProductId()),
                    cartItems.getQuantity(),
                    cartItems.getProductDto().getProductPrice()));
        }
        cartService.deleteCart(user);
    }

    @Override
    public List<OrderDto> getOrders(User user) {
        List<OrderDto> orders = new ArrayList<>();
        for (Order order : orderDao.findAllByUserOrderByCreatedDateDesc(user)) {
            List<OrderItemsDto> orderItems = new ArrayList<>();
            for (OrderItems items : orderItemsDao.findAllByOrder(order)) {
                orderItems.add(
                        new OrderItemsDto(
                                items.getOrderItemId(),
                                new ProductDto(
                                        items.getProduct().getProductId(),
                                        items.getProduct().getProductName(),
                                        items.getProduct().getProductType(),
                                        items.getProduct().getProductCount(),
                                        items.getProduct().getProductRating(),
                                        items.getProduct().getProductPrice(),
                                        items.getProduct().getManufacturerName(),
                                        items.getProduct().getTag(),
                                        items.getProduct().getProductImage(),
                                        items.getProduct().getLabel(),
                                        items.getProduct().getDescription()),
                                items.getQuantity()));
            }
            orders.add(
                    new OrderDto(
                            order.getOrderId(),
                            formatDate(order.getCreatedDate()),
                            orderItems,
                            order.getTotalPrice()));
        }

        return orders;
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(date);
    }

}