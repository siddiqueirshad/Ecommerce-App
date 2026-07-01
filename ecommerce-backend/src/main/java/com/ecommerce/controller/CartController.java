package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.entity.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart endpoints")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/add")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ApiResponse<CartItemResponse>> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddToCartRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        CartItemResponse item = cartService.addToCart(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", item));
    }

    @GetMapping
    @Operation(summary = "Get user's cart")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<CartItemResponse> cart = cartService.getCart(user.getId());
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long itemId) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        cartService.removeFromCart(user.getId(), itemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed", null));
    }
}
