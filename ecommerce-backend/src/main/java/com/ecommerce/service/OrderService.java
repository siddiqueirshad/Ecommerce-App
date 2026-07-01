package com.ecommerce.service;

import com.ecommerce.dto.CheckoutRequest;
import com.ecommerce.dto.OrderItemResponse;
import com.ecommerce.dto.OrderMapper;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.*;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse checkout(Long userId, CheckoutRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.PENDING)
                .address(request.getAddress())
                .total(BigDecimal.ZERO)
                .build();

        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException("Insufficient stock for: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(product.getId())
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotal(total);
        order.setItems(orderItems);
        order = orderRepository.save(order);

        cartItemRepository.deleteByUserId(userId);

        return toDetailedResponse(order);
    }

    public List<OrderResponse> getOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDetailedResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("Order does not belong to user");
        }

        return toDetailedResponse(order);
    }

    private OrderResponse toDetailedResponse(Order order) {
        OrderResponse response = orderMapper.toResponse(order);
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> {
                    String productName = productRepository.findById(item.getProductId())
                            .map(Product::getName)
                            .orElse("Unknown");
                    return OrderItemResponse.builder()
                            .productId(item.getProductId())
                            .productName(productName)
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
        response.setItems(items);
        return response;
    }
}
