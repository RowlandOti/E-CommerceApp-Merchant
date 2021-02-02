package com.rowland.delivery.merchant.features.dash.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rowland.delivery.merchant.databinding.RowCategoryBinding
import com.rowland.delivery.presentation.model.category.CategoryModel
import javax.inject.Inject

/**
 * Created by Rowland on 5/6/2018.
 */

class CategoryAdapter @Inject
constructor() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList: List<CategoryModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = RowCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.populate(categoryList!![position])
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) 0 else categoryList!!.size
    }

    fun setList(categoryModels: List<CategoryModel>) {
        if (this.categoryList == null) {
            this.categoryList = categoryModels
            notifyItemRangeInserted(0, categoryModels.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CategoryAdapter.categoryList!!.size
                }

                override fun getNewListSize(): Int {
                    return categoryModels.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CategoryAdapter.categoryList!![oldItemPosition].name === categoryModels[newItemPosition].name
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = categoryModels[newItemPosition]
                    val oldItem = this@CategoryAdapter.categoryList!![oldItemPosition]
                    return newItem.name === oldItem.name && newItem == oldItem
                }
            })
            this.categoryList = categoryModels
            result.dispatchUpdatesTo(this)
        }
    }

    class CategoryViewHolder(val itemViewBinding: RowCategoryBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun populate(categoryModel: CategoryModel) {
            itemViewBinding.catName.text =
                Character.toUpperCase(categoryModel.name!![0]) + categoryModel.name!!.substring(1)
        }
    }
}
