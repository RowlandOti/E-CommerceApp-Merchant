/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant.features.dash.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rowland.delivery.merchant.databinding.RowSingleOrderItemBinding
import com.rowland.delivery.merchant.features.dash.tools.recylerview.HFRecyclerView
import com.rowland.delivery.presentation.model.order.OrderItemModel

/**
 * Created by Rowland on 5/9/2018.
 */

class OrderItemAdapter : HFRecyclerView<OrderItemModel> {

    constructor() : super(null, false, false) {}

    constructor(withHeader: Boolean, withFooter: Boolean) : super(null, withHeader, withFooter) {}

    constructor(data: List<OrderItemModel>, withHeader: Boolean, withFooter: Boolean) : super(
        data,
        withHeader,
        withFooter
    ) {}

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = RowSingleOrderItemBinding
            .inflate(inflater, parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OrderItemViewHolder) {
            val data = getItem(position)
            holder.bind(data)
        } else if (holder is HeaderViewHolder) {
        } else if (holder is FooterViewHolder) {
        }
    }

    fun setList(orders: List<OrderItemModel>) {
        if (this.data == null) {
            this.data = orders
            notifyItemRangeInserted(0, orders.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@OrderItemAdapter.data.size
                }

                override fun getNewListSize(): Int {
                    return orders.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@OrderItemAdapter.data[oldItemPosition].itemCode === orders[newItemPosition].itemCode
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = orders[newItemPosition]
                    val oldItem = orders[oldItemPosition]
                    return newItem.itemCode === oldItem.itemCode && newItem == oldItem
                }
            })
            this.data = orders
            result.dispatchUpdatesTo(this)
        }
    }

    class OrderItemViewHolder(private val itemViewBinding: RowSingleOrderItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(orderItem: OrderItemModel) {
            Glide.with(itemViewBinding.foodImage.context).load(orderItem.imageUrl)
                .into(itemViewBinding.foodImage)
            itemViewBinding.itemsCount.text = orderItem.itemQuantity.toString()
            itemViewBinding.itemName.text = orderItem.title
            itemViewBinding.itemPrice.text = "Price: Ksh " + orderItem.price + " each"
            itemViewBinding.itemTotalPrice.text = "Total price: Ksh " + orderItem.itemTotal!!
        }
    }

    class HeaderViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}