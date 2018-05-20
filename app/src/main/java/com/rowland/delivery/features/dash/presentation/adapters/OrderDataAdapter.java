package com.rowland.delivery.features.dash.presentation.adapters;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.domain.models.order.OrderItem;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.utilities.DateUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 5/9/2018.
 */

public class OrderDataAdapter extends HFRecyclerView<OrderData> {

    public OrderDataAdapter() {
        super(null, false, false);
    }

    public OrderDataAdapter(boolean withHeader, boolean withFooter) {
        super(null, withHeader, withFooter);
    }

    public OrderDataAdapter(List<OrderData> data, boolean withHeader, boolean withFooter) {
        super(data, withHeader, withFooter);
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new OrderDataViewHolder(inflater.inflate(R.layout.row_single_customer_order, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderDataViewHolder) {
            OrderDataViewHolder itemHolder = (OrderDataViewHolder) holder;
            OrderData data = getItem(position);
            itemHolder.bind(data);
        } else if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof FooterViewHolder) {

        }
    }

    public void setList(final List<OrderData> orders) {
        if (this.data == null) {
            this.data = orders;
            notifyItemRangeInserted(0, orders.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OrderDataAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return orders.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return OrderDataAdapter.this.data.get(oldItemPosition).getId() ==
                            orders.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    OrderData newItem = orders.get(newItemPosition);
                    OrderData oldItem = OrderDataAdapter.this.data.get(oldItemPosition);
                    return newItem.getId() == oldItem.getId()
                            && newItem.equals(oldItem);
                }
            });
            this.data = orders;
            result.dispatchUpdatesTo(this);
        }
    }

    public static class OrderDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container_layout_customer)
        LinearLayout container_layout;

        @BindView(R.id.order_date)
        TextView order_date;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.name_text_view)
        TextView customerName;

        @BindView(R.id.unique_orders)
        TextView uniqueOrders;

        public OrderDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(OrderData orderData) {
            customerName.setText(orderData.getName());

            Date lFromDate1 = new Date(orderData.getCreatedAt());
            order_date.setText(DateUtils.getTimeAgo(lFromDate1.getTime()));

            int totalOrders = 0;
            if (orderData.getItems() != null) {
                List<OrderItem> items = orderData.getItems();
                for (OrderItem item : items) {
                    totalOrders = totalOrders + Integer.valueOf(item.getItemQuantity());
                }
            }

            uniqueOrders.setText(String.format("%s orders", totalOrders));
            status.setText(orderData.getStatus());

            switch (orderData.getStatus()) {
                case "active":
                    status.setTextColor(Color.parseColor("#378E3D"));
                    break;
                case "delivered":
                    status.setTextColor(Color.parseColor("#FF6D00"));
                    break;
                case "failed":
                    status.setTextColor(Color.parseColor("#B94464"));
                    container_layout.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    break;
                case "completed":
                    status.setTextColor(Color.parseColor("#AAAAAA"));
                    container_layout.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    break;
                case "missed":
                    status.setTextColor(Color.parseColor("#B94464"));
                    container_layout.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    break;
                case "in_progress":
                    status.setTextColor(Color.parseColor("#378E3D"));
                    break;
                case "pending":
                    status.setTextColor(Color.parseColor("#B94464"));
                    break;
                case "cancelled":
                    status.setTextColor(Color.parseColor("#B94464"));
                    container_layout.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    break;
                default:
                    status.setTextColor(Color.parseColor("#AAAAAA"));
            }
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
