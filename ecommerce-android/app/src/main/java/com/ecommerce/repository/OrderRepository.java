package com.ecommerce.repository;

import com.ecommerce.model.*;
import com.ecommerce.network.ApiService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class OrderRepository {

    private final ApiService apiService;

    @Inject
    public OrderRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public CartItem addToCart(long productId, int quantity) throws IOException {
        Response<ApiResponse<CartItem>> response =
                apiService.addToCart(new AddToCartRequest(productId, quantity)).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        throw new IOException("Failed to add to cart");
    }

    public List<CartItem> getCart() throws IOException {
        Response<ApiResponse<List<CartItem>>> response = apiService.getCart().execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        throw new IOException("Failed to load cart");
    }

    public void removeFromCart(long itemId) throws IOException {
        Response<ApiResponse<Void>> response = apiService.removeFromCart(itemId).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Failed to remove item");
        }
    }

    public Order checkout(String address) throws IOException {
        Response<ApiResponse<Order>> response =
                apiService.checkout(new CheckoutRequest(address)).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        throw new IOException("Checkout failed");
    }

    public List<Order> getOrders() throws IOException {
        Response<ApiResponse<List<Order>>> response = apiService.getOrders().execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        throw new IOException("Failed to load orders");
    }
}
