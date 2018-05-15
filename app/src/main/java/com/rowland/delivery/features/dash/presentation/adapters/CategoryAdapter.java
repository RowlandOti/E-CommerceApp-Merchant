package com.rowland.delivery.features.dash.presentation.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rowland.delivery.features.dash.domain.models.category.Category;
import com.rowland.delivery.merchant.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rowland on 5/6/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;

    @Inject
    public CategoryAdapter() {

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.populate(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    public void setList(final List<Category> categories) {
        if (this.categoryList == null) {
            this.categoryList = categories;
            notifyItemRangeInserted(0, categories.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CategoryAdapter.this.categoryList.size();
                }

                @Override
                public int getNewListSize() {
                    return categories.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CategoryAdapter.this.categoryList.get(oldItemPosition).getName() ==
                            categories.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Category newItem = categories.get(newItemPosition);
                    Category oldItem = categories.get(oldItemPosition);
                    return newItem.getName() == oldItem.getName()
                            && newItem.equals(oldItem);
                }
            });
            this.categoryList = categories;
            result.dispatchUpdatesTo(this);
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cat_name)
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Category category) {
            categoryName.setText(Character.toUpperCase(category.getName().charAt(0)) + category.getName().substring(1));
        }
    }
}
