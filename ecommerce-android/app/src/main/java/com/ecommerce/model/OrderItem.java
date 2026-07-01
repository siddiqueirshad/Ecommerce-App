package com.ecommerce.model;

import java.math.BigDecimal;

public class OrderItem {
    private long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
