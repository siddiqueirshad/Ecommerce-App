package com.ecommerce.network;

import com.ecommerce.model.*;
import com.ecommerce.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse<AuthResponse>> register(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse<AuthResponse>> login(@Body LoginRequest request);

    @POST("api/auth/refresh")
    Call<ApiResponse<AuthResponse>> refresh(@Header("Authorization") String token);

    @GET("api/products")
    Call<ApiResponse<PageResponse<Product>>> getProducts(
            @Query("page") int page,
            @Query("size") int size,
            @Query("category") Long category,
            @Query("search") String search);

    @GET("api/products/{id}")
    Call<ApiResponse<Product>> getProduct(@Path("id") long id);

    @GET("api/categories")
    Call<ApiResponse<List<Category>>> getCategories();

    @POST("api/cart/add")
    Call<ApiResponse<CartItem>> addToCart(@Body AddToCartRequest request);

    @GET("api/cart")
    Call<ApiResponse<List<CartItem>>> getCart();

    @DELETE("api/cart/{itemId}")
    Call<ApiResponse<Void>> removeFromCart(@Path("itemId") long itemId);

    @POST("api/orders/checkout")
    Call<ApiResponse<Order>> checkout(@Body CheckoutRequest request);

    @GET("api/orders")
    Call<ApiResponse<List<Order>>> getOrders();

    @GET("api/orders/{id}")
    Call<ApiResponse<Order>> getOrder(@Path("id") long id);
}
