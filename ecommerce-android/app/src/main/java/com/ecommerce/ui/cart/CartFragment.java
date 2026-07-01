package com.ecommerce.ui.cart;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecommerce.R;
import com.ecommerce.adapter.CartAdapter;
import com.ecommerce.databinding.FragmentCartBinding;
import com.ecommerce.model.CartItem;
import com.ecommerce.utils.CurrencyUtils;
import com.ecommerce.viewmodel.CartViewModel;

import java.math.BigDecimal;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment implements CartAdapter.OnCartActionListener {

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        adapter = new CartAdapter(this);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerCart.setAdapter(adapter);

        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), this::updateCart);

        cartViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        binding.btnCheckout.setOnClickListener(v ->
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_cart_to_checkout));

        cartViewModel.loadCart();
    }

    private void updateCart(List<CartItem> items) {
        adapter.setItems(items);

        if (items == null || items.isEmpty()) {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.recyclerCart.setVisibility(View.GONE);
            binding.btnCheckout.setEnabled(false);
            binding.cartTotal.setText(CurrencyUtils.format(BigDecimal.ZERO));
        } else {
            binding.emptyCart.setVisibility(View.GONE);
            binding.recyclerCart.setVisibility(View.VISIBLE);
            binding.btnCheckout.setEnabled(true);

            BigDecimal total = BigDecimal.ZERO;
            for (CartItem item : items) {
                total = total.add(item.getSubtotal());
            }
            binding.cartTotal.setText(CurrencyUtils.format(total));
        }
    }

    @Override
    public void onRemove(CartItem item) {
        cartViewModel.removeFromCart(item.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
