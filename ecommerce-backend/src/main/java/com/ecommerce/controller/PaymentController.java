package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.entity.User;
import com.ecommerce.service.PaymentService;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment processing endpoints")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping("/initiate")
    @Operation(summary = "Initiate payment for an order")
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PaymentInitiateRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        PaymentResponse payment = paymentService.initiatePayment(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Payment initiated", payment));
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify payment completion")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PaymentVerifyRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        PaymentResponse payment = paymentService.verifyPayment(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Payment verified", payment));
    }
}
