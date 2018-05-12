package com.rowland.delivery.features.dash.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rowland.delivery.features.dash.domain.models.order.OrderItem;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView;
import com.rowland.delivery.merchant.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 5/9/2018.
 */

public class OrderItemAdapter extends HFRecyclerView<OrderItem> {

    public OrderItemAdapter() {
        super(null, false, false);
    }

    public OrderItemAdapter(boolean withHeader, boolean withFooter) {
        super(null, withHeader, withFooter);
    }

    public OrderItemAdapter(List<OrderItem> data, boolean withHeader, boolean withFooter) {
        super(data, withHeader, withFooter);
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new OrderItemViewHolder(inflater.inflate(R.layout.row_single_order_item, parent, false));
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
        if (holder instanceof OrderItemViewHolder) {
            OrderItemViewHolder itemHolder = (OrderItemViewHolder) holder;
            OrderItem data = getItem(position);
            itemHolder.bind(data);
        } else if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof FooterViewHolder) {

        }
    }

    public void setList(final List<OrderItem> orders) {
        if (this.data == null) {
            this.data = orders;
            notifyItemRangeInserted(0, orders.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OrderItemAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return orders.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return OrderItemAdapter.this.data.get(oldItemPosition).getItemCode() ==
                            orders.get(newItemPosition).getItemCode();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    OrderItem newItem = orders.get(newItemPosition);
                    OrderItem oldItem = orders.get(oldItemPosition);
                    return newItem.getItemCode() == oldItem.getItemCode()
                            && newItem.equals(oldItem);
                }
            });
            this.data = orders;
            result.dispatchUpdatesTo(this);
        }
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.food_image)
        ImageView food_image;

        @BindView(R.id.items_count)
        TextView items_count;

        @BindView(R.id.item_name)
        TextView item_name;

        @BindView(R.id.item_price)
        TextView item_price;

        @BindView(R.id.item_total_price)
        TextView item_total_price;


        public OrderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(OrderItem orderItem) {
            Glide.with(food_image.getContext()).load(orderItem.getImageUrl()).into(food_image);
            items_count.setText(String.valueOf(orderItem.getItemQuantity()));
            item_name.setText(orderItem.getTitle());
            item_price.setText("Price: Ksh " + orderItem.getPrice() + " each");
            item_total_price.setText("Total price: Ksh " + orderItem.getItemTotal());
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
