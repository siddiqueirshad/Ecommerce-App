package com.ecommerce.service;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItemResponse addToCart(Long userId, AddToCartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new BadRequestException("Insufficient stock");
        }

        CartItem cartItem = cartItemRepository
                .findByUserIdAndProductId(userId, request.getProductId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = CartItem.builder()
                    .userId(userId)
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .build();
        }

        cartItem = cartItemRepository.save(cartItem);
        return toResponse(cartItem, product);
    }

    public List<CartItemResponse> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    return toResponse(item, product);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFromCart(Long userId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getUserId().equals(userId)) {
            throw new BadRequestException("Cart item does not belong to user");
        }

        cartItemRepository.delete(cartItem);
    }

    private CartItemResponse toResponse(CartItem item, Product product) {
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();
    }
}
