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
import androidx.navigation.Navigation;

import com.ecommerce.R;
import com.ecommerce.databinding.FragmentCheckoutBinding;
import com.ecommerce.viewmodel.CartViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckoutFragment extends Fragment {

    private FragmentCheckoutBinding binding;
    private CartViewModel cartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        cartViewModel.getCheckoutResult().observe(getViewLifecycleOwner(), order -> {
            if (order != null) {
                Toast.makeText(requireContext(),
                        "Order #" + order.getId() + " placed!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_checkout_to_orders);
            }
        });

        cartViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        cartViewModel.getLoading().observe(getViewLifecycleOwner(), loading ->
                binding.btnPlaceOrder.setEnabled(!loading));

        binding.btnPlaceOrder.setOnClickListener(v -> {
            String address = binding.etAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(requireContext(), "Enter shipping address", Toast.LENGTH_SHORT).show();
                return;
            }
            cartViewModel.checkout(address);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
