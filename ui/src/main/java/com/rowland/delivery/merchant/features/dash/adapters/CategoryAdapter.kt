package com.rowland.delivery.merchant.features.dash.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.rowland.delivery.domain.models.category.CategoryEntity
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.row_category.view.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/6/2018.
 */

class CategoryAdapter @Inject
constructor() : androidx.recyclerview.widget.RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryEntityList: List<CategoryEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.populate(categoryEntityList!![position])
    }

    override fun getItemCount(): Int {
        return if (categoryEntityList == null) 0 else categoryEntityList!!.size
    }

    fun setList(categoryEntities: List<CategoryEntity>) {
        if (this.categoryEntityList == null) {
            this.categoryEntityList = categoryEntities
            notifyItemRangeInserted(0, categoryEntities.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CategoryAdapter.categoryEntityList!!.size
                }

                override fun getNewListSize(): Int {
                    return categoryEntities.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CategoryAdapter.categoryEntityList!![oldItemPosition].name === categoryEntities[newItemPosition].name
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = categoryEntities[newItemPosition]
                    val oldItem = this@CategoryAdapter.categoryEntityList!![oldItemPosition]
                    return newItem.name === oldItem.name && newItem == oldItem
                }
            })
            this.categoryEntityList = categoryEntities
            result.dispatchUpdatesTo(this)
        }
    }

    class CategoryViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun populate(categoryEntity: CategoryEntity) {
            itemView.cat_name!!.text = Character.toUpperCase(categoryEntity.name!![0]) + categoryEntity.name!!.substring(1)
        }
    }
}
