package com.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.R;
import com.ecommerce.model.CartItem;
import com.ecommerce.utils.CurrencyUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartActionListener {
        void onRemove(CartItem item);
    }

    private final List<CartItem> items = new ArrayList<>();
    private final OnCartActionListener listener;

    public CartAdapter(OnCartActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CartItem> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.name.setText(item.getProductName());
        holder.quantity.setText(holder.itemView.getContext().getString(R.string.qty, item.getQuantity()));
        holder.subtotal.setText(CurrencyUtils.format(item.getSubtotal()));

        if (item.getImageUrl() != null) {
            Glide.with(holder.image.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.image);
        }

        holder.removeBtn.setOnClickListener(v -> listener.onRemove(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, quantity, subtotal;
        MaterialButton removeBtn;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartItemImage);
            name = itemView.findViewById(R.id.cartItemName);
            quantity = itemView.findViewById(R.id.cartItemQuantity);
            subtotal = itemView.findViewById(R.id.cartItemSubtotal);
            removeBtn = itemView.findViewById(R.id.btnRemove);
        }
    }
}
