package com.ecommerce.model;

import java.math.BigDecimal;

public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private Long categoryId;
    private String imageUrl;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    public Long getCategoryId() { return categoryId; }
    public String getImageUrl() { return imageUrl; }
}
