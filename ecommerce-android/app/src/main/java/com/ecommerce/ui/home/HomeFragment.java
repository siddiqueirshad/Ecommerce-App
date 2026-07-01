package com.ecommerce.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ecommerce.R;
import com.ecommerce.adapter.ProductAdapter;
import com.ecommerce.databinding.FragmentHomeBinding;
import com.ecommerce.model.Product;
import com.ecommerce.viewmodel.ProductViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private FragmentHomeBinding binding;
    private ProductViewModel productViewModel;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        adapter = new ProductAdapter(this);
        binding.recyclerProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerProducts.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(() ->
                productViewModel.loadProducts(null, null, true));

        productViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) adapter.setProducts(products);
        });

        productViewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            binding.swipeRefresh.setRefreshing(loading);
            binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        });

        productViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        productViewModel.loadProducts(null, null, true);
    }

    @Override
    public void onProductClick(Product product) {
        Bundle args = new Bundle();
        args.putLong("productId", product.getId());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_home_to_productDetail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
