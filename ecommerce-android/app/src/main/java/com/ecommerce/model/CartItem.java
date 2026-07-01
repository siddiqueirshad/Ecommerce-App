package com.ecommerce.model;

import java.math.BigDecimal;

public class CartItem {
    private long id;
    private long productId;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;

    public long getId() { return id; }
    public long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getImageUrl() { return imageUrl; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public BigDecimal getSubtotal() { return subtotal; }
}
