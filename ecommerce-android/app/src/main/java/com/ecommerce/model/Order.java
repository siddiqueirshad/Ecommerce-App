package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private long id;
    private String status;
    private BigDecimal total;
    private String address;
    private String createdAt;
    private List<OrderItem> items;

    public long getId() { return id; }
    public String getStatus() { return status; }
    public BigDecimal getTotal() { return total; }
    public String getAddress() { return address; }
    public String getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}
