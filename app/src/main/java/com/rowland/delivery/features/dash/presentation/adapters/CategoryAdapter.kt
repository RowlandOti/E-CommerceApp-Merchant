package com.rowland.delivery.features.dash.presentation.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.rowland.delivery.features.dash.domain.models.category.Category
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.row_category.view.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/6/2018.
 */

class CategoryAdapter @Inject
constructor() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.populate(categoryList!![position])
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) 0 else categoryList!!.size
    }

    fun setList(categories: List<Category>) {
        if (this.categoryList == null) {
            this.categoryList = categories
            notifyItemRangeInserted(0, categories.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CategoryAdapter.categoryList!!.size
                }

                override fun getNewListSize(): Int {
                    return categories.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CategoryAdapter.categoryList!![oldItemPosition].name === categories[newItemPosition].name
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = categories[newItemPosition]
                    val oldItem = this@CategoryAdapter.categoryList!![oldItemPosition]
                    return newItem.name === oldItem.name && newItem == oldItem
                }
            })
            this.categoryList = categories
            result.dispatchUpdatesTo(this)
        }
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun populate(category: Category) {
            itemView.cat_name!!.text = Character.toUpperCase(category.name!![0]) + category.name!!.substring(1)
        }
    }
}
