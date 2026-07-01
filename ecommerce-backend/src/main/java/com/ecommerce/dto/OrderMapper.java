package com.ecommerce.dto;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    OrderItemResponse toItemResponse(OrderItem item);

    @Mapping(target = "status", expression = "java(payment.getStatus().name())")
    PaymentResponse toPaymentResponse(Payment payment);
}
