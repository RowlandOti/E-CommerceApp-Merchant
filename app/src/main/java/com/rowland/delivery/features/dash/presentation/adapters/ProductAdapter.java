package com.rowland.delivery.features.dash.presentation.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.rowland.delivery.features.dash.domain.models.product.Product;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView;
import com.rowland.delivery.merchant.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 5/13/2018.
 */

public class ProductAdapter extends HFRecyclerView<Product> {

    ViewBinderHelper viewBinderHelper;

    public ProductAdapter() {
        this(false, false);
        viewBinderHelper = new ViewBinderHelper();
        viewBinderHelper.setOpenOnlyOne(true);
    }

    public ProductAdapter(boolean withHeader, boolean withFooter) {
        this(null, withHeader, withFooter);
    }

    public ProductAdapter(List<Product> data, boolean withHeader, boolean withFooter) {
        super(data, withHeader, withFooter);
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        ProductViewHolder x = new ProductViewHolder(inflater.inflate(R.layout.row_single_product, parent, false));
        return new ProductViewHolder(inflater.inflate(R.layout.row_single_product, parent, false));
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
        if (holder instanceof ProductViewHolder) {
            ProductViewHolder itemHolder = (ProductViewHolder) holder;
            Product data = getItem(position);
            itemHolder.bind(data);

        } else if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof FooterViewHolder) {

        }
    }

    public void setList(final List<Product> products) {
        if (this.data == null) {
            this.data = products;
            notifyItemRangeInserted(0, products.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProductAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return products.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ProductAdapter.this.data.get(oldItemPosition).getFirestoreUid() ==
                            products.get(newItemPosition).getFirestoreUid();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product newItem = products.get(newItemPosition);
                    Product oldItem = products.get(oldItemPosition);
                    return newItem.getFirestoreUid() == oldItem.getFirestoreUid()
                            && newItem.equals(oldItem);
                }
            });
            this.data = products;
            result.dispatchUpdatesTo(this);
        }
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swpRevealLayout;

        @BindView(R.id.product_imageview)
        ImageView productImage;

        @BindView(R.id.product_name_textview)
        TextView productName;

        @BindView(R.id.product_description)
        TextView productDescription;

        @BindView(R.id.product_price)
        TextView productPrice;

        @BindView(R.id.txt_unpublish)
        TextView txtUnpublish;

        @BindView(R.id.txt_edit)
        TextView txtEdit;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Product product) {
            Glide.with(productImage.getContext()).load(product.getImageUrl()).centerCrop().crossFade().into(productImage);
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText("Ksh " + product.getPrice());

            viewBinderHelper.bind(swpRevealLayout, product.getFirestoreUid());
            txtUnpublish.setOnClickListener(v -> {
                //
                viewBinderHelper.closeLayout(product.getFirestoreUid());
            });

            txtEdit.setOnClickListener(v -> {
                //
                viewBinderHelper.closeLayout(product.getFirestoreUid());
            });
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
