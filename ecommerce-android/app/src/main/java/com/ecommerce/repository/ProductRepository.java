package com.ecommerce.repository;

import com.ecommerce.db.CachedProduct;
import com.ecommerce.db.ProductDao;
import com.ecommerce.model.*;
import com.ecommerce.network.ApiService;
import com.ecommerce.utils.Constants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class ProductRepository {

    private final ApiService apiService;
    private final ProductDao productDao;

    @Inject
    public ProductRepository(ApiService apiService, ProductDao productDao) {
        this.apiService = apiService;
        this.productDao = productDao;
    }

    public PageResponse<Product> getProducts(int page, Long category, String search) throws IOException {
        Response<ApiResponse<PageResponse<Product>>> response =
                apiService.getProducts(page, Constants.PAGE_SIZE, category, search).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            PageResponse<Product> pageResponse = response.body().getData();
            cacheProducts(pageResponse.getContent());
            return pageResponse;
        }
        throw new IOException("Failed to load products");
    }

    public Product getProduct(long id) throws IOException {
        Response<ApiResponse<Product>> response = apiService.getProduct(id).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            Product product = response.body().getData();
            cacheProduct(product);
            return product;
        }
        throw new IOException("Failed to load product");
    }

    public List<Category> getCategories() throws IOException {
        Response<ApiResponse<List<Category>>> response = apiService.getCategories().execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        throw new IOException("Failed to load categories");
    }

    public List<Product> getCachedProducts() {
        List<CachedProduct> cached = productDao.getAll();
        List<Product> products = new ArrayList<>();
        for (CachedProduct cp : cached) {
            Product p = new Product();
            // Use reflection-free manual mapping via a helper
            products.add(fromCached(cp));
        }
        return products;
    }

    private void cacheProducts(List<Product> products) {
        if (products == null) return;
        List<CachedProduct> cached = new ArrayList<>();
        for (Product p : products) {
            cached.add(toCached(p));
        }
        productDao.insertAll(cached);
    }

    private void cacheProduct(Product product) {
        productDao.insert(toCached(product));
    }

    private CachedProduct toCached(Product p) {
        CachedProduct cp = new CachedProduct();
        cp.id = p.getId();
        cp.name = p.getName();
        cp.description = p.getDescription();
        cp.price = p.getPrice() != null ? p.getPrice().toString() : "0";
        cp.stock = p.getStock();
        cp.categoryId = p.getCategoryId();
        cp.imageUrl = p.getImageUrl();
        cp.cachedAt = System.currentTimeMillis();
        return cp;
    }

    private Product fromCached(CachedProduct cp) {
        Product p = new Product() {
            @Override public long getId() { return cp.id; }
            @Override public String getName() { return cp.name; }
            @Override public String getDescription() { return cp.description; }
            @Override public BigDecimal getPrice() { return new BigDecimal(cp.price); }
            @Override public int getStock() { return cp.stock; }
            @Override public Long getCategoryId() { return cp.categoryId; }
            @Override public String getImageUrl() { return cp.imageUrl; }
        };
        return p;
    }
}
