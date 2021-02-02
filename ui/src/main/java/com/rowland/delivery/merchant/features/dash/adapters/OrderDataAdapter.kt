package com.rowland.delivery.merchant.features.dash.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rowland.delivery.merchant.databinding.RowSingleCustomerOrderBinding
import com.rowland.delivery.merchant.features.dash.tools.recylerview.HFRecyclerView
import com.rowland.delivery.merchant.utilities.DateUtils
import com.rowland.delivery.presentation.model.order.OrderDataModel
import java.util.*

/**
 * Created by Rowland on 5/9/2018.
 */

class OrderDataAdapter : HFRecyclerView<OrderDataModel> {

    constructor() : super(null, false, false) {}

    constructor(withHeader: Boolean, withFooter: Boolean) : super(null, withHeader, withFooter) {}

    constructor(data: List<OrderDataModel>, withHeader: Boolean, withFooter: Boolean) : super(
        data,
        withHeader,
        withFooter
    ) {
    }

    override fun getItemView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = RowSingleCustomerOrderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDataViewHolder(binding)
    }

    override fun getHeaderView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is OrderDataViewHolder) {
            val data = getItem(position)
            holder.bind(data)
        } else if (holder is HeaderViewHolder) {

        } else if (holder is FooterViewHolder) {

        }
    }

    fun setList(orders: List<OrderDataModel>) {
        if (this.data == null) {
            this.data = orders
            notifyItemRangeInserted(0, orders.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@OrderDataAdapter.data.size
                }

                override fun getNewListSize(): Int {
                    return orders.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@OrderDataAdapter.data[oldItemPosition].id === orders[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = orders[newItemPosition]
                    val oldItem = this@OrderDataAdapter.data[oldItemPosition]
                    return newItem.id === oldItem.id && newItem == oldItem
                }
            })
            this.data = orders
            result.dispatchUpdatesTo(this)
        }
    }

    class OrderDataViewHolder(private val itemViewBinding: RowSingleCustomerOrderBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(orderData: OrderDataModel) {
            itemViewBinding.customerName.text = orderData.name

            val lFromDate1 = Date(orderData.createdAt)
            itemViewBinding.orderDate.text = DateUtils.getTimeAgo(lFromDate1.time)

            var totalOrders = 0
            if (orderData.items != null) {
                val items = orderData.items
                for (item in items!!) {
                    totalOrders += Integer.valueOf(item.itemQuantity!!)
                }
            }

            itemViewBinding.uniqueOrders.text = String.format("%s orders", totalOrders)
            itemViewBinding.status.text = orderData.status

            when (orderData.status) {
                "active" -> itemViewBinding.status.setTextColor(Color.parseColor("#378E3D"))
                "delivered" -> itemViewBinding.status.setTextColor(Color.parseColor("#FF6D00"))
                "failed" -> {
                    itemViewBinding.status.setTextColor(Color.parseColor("#B94464"))
                    itemViewBinding.containerCardlayout.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "completed" -> {
                    itemViewBinding.status.setTextColor(Color.parseColor("#AAAAAA"))
                    itemViewBinding.containerCardlayout.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "missed" -> {
                    itemViewBinding.status.setTextColor(Color.parseColor("#B94464"))
                    itemViewBinding.containerCardlayout.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "in_progress" -> itemViewBinding.status.setTextColor(Color.parseColor("#378E3D"))
                "pending" -> itemViewBinding.status.setTextColor(Color.parseColor("#B94464"))
                "cancelled" -> {
                    itemViewBinding.status.setTextColor(Color.parseColor("#B94464"))
                    itemViewBinding.containerCardlayout.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                else -> itemViewBinding.status.setTextColor(Color.parseColor("#AAAAAA"))
            }
        }
    }

    class HeaderViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
