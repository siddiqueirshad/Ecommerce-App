package com.ecommerce.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ecommerce.R;
import com.ecommerce.databinding.FragmentProductDetailBinding;
import com.ecommerce.utils.CurrencyUtils;
import com.ecommerce.viewmodel.CartViewModel;
import com.ecommerce.viewmodel.ProductViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding binding;
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;
    private long productId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        if (getArguments() != null) {
            productId = getArguments().getLong("productId");
        }

        productViewModel.getSelectedProduct().observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                binding.productName.setText(product.getName());
                binding.productDescription.setText(product.getDescription());
                binding.productPrice.setText(CurrencyUtils.format(product.getPrice()));
                if (product.getStock() > 0) {
                    binding.productStock.setText(getString(R.string.in_stock));
                    binding.btnAddToCart.setEnabled(true);
                } else {
                    binding.productStock.setText(getString(R.string.out_of_stock));
                    binding.btnAddToCart.setEnabled(false);
                }

                if (product.getImageUrl() != null) {
                    Glide.with(this)
                            .load(product.getImageUrl())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(binding.productImage);
                }
            }
        });

        cartViewModel.getMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        });

        cartViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        binding.btnAddToCart.setOnClickListener(v ->
                cartViewModel.addToCart(productId, 1));

        productViewModel.loadProduct(productId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
