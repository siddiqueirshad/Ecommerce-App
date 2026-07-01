package com.ecommerce.model;

public class Category {
    private long id;
    private String name;
    private Long parentId;

    public long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
}
