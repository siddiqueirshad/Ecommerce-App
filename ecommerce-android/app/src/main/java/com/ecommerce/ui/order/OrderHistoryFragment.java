package com.ecommerce.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecommerce.adapter.OrderAdapter;
import com.ecommerce.databinding.FragmentOrderHistoryBinding;
import com.ecommerce.viewmodel.CartViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderHistoryFragment extends Fragment {

    private FragmentOrderHistoryBinding binding;
    private CartViewModel cartViewModel;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        adapter = new OrderAdapter();
        binding.recyclerOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerOrders.setAdapter(adapter);

        cartViewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter.setOrders(orders);
            binding.emptyOrders.setVisibility(
                    orders == null || orders.isEmpty() ? View.VISIBLE : View.GONE);
        });

        cartViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        cartViewModel.loadOrders();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
