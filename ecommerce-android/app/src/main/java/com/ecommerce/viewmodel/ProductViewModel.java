package com.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ecommerce.model.Category;
import com.ecommerce.model.PageResponse;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductViewModel extends ViewModel {

    private final ProductRepository productRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<Product> selectedProduct = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> hasMore = new MutableLiveData<>(true);

    private int currentPage = 0;

    @Inject
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public LiveData<List<Product>> getProducts() { return products; }
    public LiveData<Product> getSelectedProduct() { return selectedProduct; }
    public LiveData<List<Category>> getCategories() { return categories; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Boolean> getHasMore() { return hasMore; }

    public void loadProducts(Long category, String search, boolean refresh) {
        if (refresh) {
            currentPage = 0;
            hasMore.setValue(true);
        }

        if (Boolean.FALSE.equals(hasMore.getValue()) && !refresh) return;

        loading.setValue(true);
        final int page = currentPage;
        executor.execute(() -> {
            try {
                PageResponse<Product> response = productRepository.getProducts(page, category, search);
                if (refresh || page == 0) {
                    products.postValue(response.getContent());
                } else {
                    List<Product> current = products.getValue();
                    if (current != null) {
                        current.addAll(response.getContent());
                        products.postValue(current);
                    } else {
                        products.postValue(response.getContent());
                    }
                }
                hasMore.postValue(!response.isLast());
                currentPage = page + 1;
            } catch (Exception e) {
                if (page == 0) {
                    products.postValue(productRepository.getCachedProducts());
                }
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void loadProduct(long id) {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                selectedProduct.postValue(productRepository.getProduct(id));
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void loadCategories() {
        executor.execute(() -> {
            try {
                categories.postValue(productRepository.getCategories());
            } catch (Exception e) {
                error.postValue(e.getMessage());
            }
        });
    }
}
