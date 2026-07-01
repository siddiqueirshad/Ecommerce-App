package com.ecommerce.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CachedProduct> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CachedProduct product);

    @Query("SELECT * FROM cached_products ORDER BY name ASC")
    List<CachedProduct> getAll();

    @Query("SELECT * FROM cached_products WHERE id = :id")
    CachedProduct getById(long id);

    @Query("DELETE FROM cached_products")
    void clearAll();
}
