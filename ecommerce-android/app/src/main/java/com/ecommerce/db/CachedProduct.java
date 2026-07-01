package com.ecommerce.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "cached_products")
public class CachedProduct {
    @PrimaryKey
    public long id;
    public String name;
    public String description;
    public String price;
    public int stock;
    public Long categoryId;
    public String imageUrl;
    public long cachedAt;
}
