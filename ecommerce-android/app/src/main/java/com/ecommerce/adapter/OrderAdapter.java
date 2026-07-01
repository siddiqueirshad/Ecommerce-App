package com.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.R;
import com.ecommerce.model.Order;
import com.ecommerce.utils.CurrencyUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> newOrders) {
        orders.clear();
        if (newOrders != null) orders.addAll(newOrders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderId.setText(holder.itemView.getContext().getString(R.string.order_number, order.getId()));
        holder.status.setText(order.getStatus());
        holder.total.setText(CurrencyUtils.format(order.getTotal()));
        holder.date.setText(order.getCreatedAt() != null ? order.getCreatedAt() : "");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, status, total, date;

        ViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            status = itemView.findViewById(R.id.orderStatus);
            total = itemView.findViewById(R.id.orderTotal);
            date = itemView.findViewById(R.id.orderDate);
        }
    }
}
