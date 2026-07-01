package com.ecommerce.service;

import com.ecommerce.dto.OrderMapper;
import com.ecommerce.dto.PaymentInitiateRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.dto.PaymentVerifyRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public PaymentResponse initiatePayment(Long userId, PaymentInitiateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("Order does not belong to user");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Order is not in pending status");
        }

        paymentRepository.findByOrderId(order.getId()).ifPresent(p -> {
            throw new BadRequestException("Payment already initiated for this order");
        });

        String gatewayRef = "PAY_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();

        Payment payment = Payment.builder()
                .orderId(order.getId())
                .gatewayRef(gatewayRef)
                .status(PaymentStatus.PENDING)
                .amount(order.getTotal())
                .build();

        payment = paymentRepository.save(payment);
        return orderMapper.toPaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse verifyPayment(Long userId, PaymentVerifyRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("Payment does not belong to user");
        }

        if (!payment.getGatewayRef().equals(request.getGatewayRef())) {
            throw new BadRequestException("Invalid gateway reference");
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return orderMapper.toPaymentResponse(payment);
    }
}
