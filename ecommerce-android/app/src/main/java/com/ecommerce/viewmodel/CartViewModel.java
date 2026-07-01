package com.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private final OrderRepository orderRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>();
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>();
    private final MutableLiveData<Order> checkoutResult = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    @Inject
    public CartViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public LiveData<List<CartItem>> getCartItems() { return cartItems; }
    public LiveData<List<Order>> getOrders() { return orders; }
    public LiveData<Order> getCheckoutResult() { return checkoutResult; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void addToCart(long productId, int quantity) {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                orderRepository.addToCart(productId, quantity);
                message.postValue("Added to cart");
                loadCart();
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void loadCart() {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                cartItems.postValue(orderRepository.getCart());
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void removeFromCart(long itemId) {
        executor.execute(() -> {
            try {
                orderRepository.removeFromCart(itemId);
                loadCart();
            } catch (Exception e) {
                error.postValue(e.getMessage());
            }
        });
    }

    public void checkout(String address) {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                Order order = orderRepository.checkout(address);
                checkoutResult.postValue(order);
                cartItems.postValue(null);
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void loadOrders() {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                orders.postValue(orderRepository.getOrders());
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }
}
