package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentVerifyRequest {

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotBlank(message = "Gateway reference is required")
    private String gatewayRef;
}
