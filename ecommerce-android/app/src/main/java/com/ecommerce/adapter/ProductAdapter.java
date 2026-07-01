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
import com.ecommerce.model.Product;
import com.ecommerce.utils.CurrencyUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private final List<Product> products = new ArrayList<>();
    private final OnProductClickListener listener;

    public ProductAdapter(OnProductClickListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> newProducts) {
        products.clear();
        if (newProducts != null) products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public void addProducts(List<Product> more) {
        if (more != null) {
            int start = products.size();
            products.addAll(more);
            notifyItemRangeInserted(start, more.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(CurrencyUtils.format(product.getPrice()));
        if (product.getStock() > 0) {
            holder.stock.setText(holder.itemView.getContext().getString(R.string.in_stock));
            holder.stock.setBackgroundResource(R.drawable.bg_stock_badge);
        } else {
            holder.stock.setText(holder.itemView.getContext().getString(R.string.out_of_stock));
            holder.stock.setBackgroundColor(holder.itemView.getContext().getColor(R.color.error));
        }

        if (product.getImageUrl() != null) {
            Glide.with(holder.image.getContext())
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, stock;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            stock = itemView.findViewById(R.id.productStock);
        }
    }
}
