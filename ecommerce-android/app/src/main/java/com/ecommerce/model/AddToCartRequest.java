package com.ecommerce.model;

public class AddToCartRequest {
    private long productId;
    private int quantity;

    public AddToCartRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
