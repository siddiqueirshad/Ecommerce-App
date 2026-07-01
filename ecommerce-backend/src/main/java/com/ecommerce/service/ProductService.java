package com.ecommerce.service;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public PageResponse<ProductResponse> getProducts(int page, int size, Long categoryId, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByFilters(categoryId, search, pageable);

        return PageResponse.<ProductResponse>builder()
                .content(productMapper.toResponseList(productPage.getContent()))
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return productMapper.toResponse(product);
    }

    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toResponseList(categories);
    }
}
