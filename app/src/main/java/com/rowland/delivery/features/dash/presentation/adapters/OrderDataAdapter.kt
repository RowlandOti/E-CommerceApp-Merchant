package com.rowland.delivery.features.dash.presentation.adapters


import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.rowland.delivery.features.dash.domain.models.order.OrderData
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView
import com.rowland.delivery.merchant.R
import com.rowland.delivery.utilities.DateUtils
import kotlinx.android.synthetic.main.row_single_customer_order.view.*
import java.util.*

/**
 * Created by Rowland on 5/9/2018.
 */

class OrderDataAdapter : HFRecyclerView<OrderData> {

    constructor() : super(null, false, false) {}

    constructor(withHeader: Boolean, withFooter: Boolean) : super(null, withHeader, withFooter) {}

    constructor(data: List<OrderData>, withHeader: Boolean, withFooter: Boolean) : super(data, withHeader, withFooter) {}

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return OrderDataViewHolder(inflater.inflate(R.layout.row_single_customer_order, parent, false))
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OrderDataViewHolder) {
            val data = getItem(position)
            holder.bind(data)
        } else if (holder is HeaderViewHolder) {

        } else if (holder is FooterViewHolder) {

        }
    }

    fun setList(orders: List<OrderData>) {
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

    class OrderDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(orderData: OrderData) {
            itemView.customer_name!!.text = orderData.name

            val lFromDate1 = Date(orderData.createdAt)
            itemView.order_date!!.text = DateUtils.getTimeAgo(lFromDate1.time)

            var totalOrders = 0
            if (orderData.items != null) {
                val items = orderData.items
                for (item in items!!) {
                    totalOrders = totalOrders + Integer.valueOf(item.itemQuantity!!)
                }
            }

            itemView.unique_orders!!.text = String.format("%s orders", totalOrders)
            itemView.status!!.text = orderData.status

            when (orderData.status) {
                "active" -> itemView.status!!.setTextColor(Color.parseColor("#378E3D"))
                "delivered" -> itemView.status!!.setTextColor(Color.parseColor("#FF6D00"))
                "failed" -> {
                    itemView.status!!.setTextColor(Color.parseColor("#B94464"))
                    itemView.container_cardlayout!!.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "completed" -> {
                    itemView.status!!.setTextColor(Color.parseColor("#AAAAAA"))
                    itemView.container_cardlayout!!.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "missed" -> {
                    itemView.status!!.setTextColor(Color.parseColor("#B94464"))
                    itemView.container_cardlayout!!.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                "in_progress" -> itemView.status!!.setTextColor(Color.parseColor("#378E3D"))
                "pending" -> itemView.status!!.setTextColor(Color.parseColor("#B94464"))
                "cancelled" -> {
                    itemView.status!!.setTextColor(Color.parseColor("#B94464"))
                    itemView.container_cardlayout!!.setBackgroundColor(Color.parseColor("#F0F0F0"))
                }
                else -> itemView.status!!.setTextColor(Color.parseColor("#AAAAAA"))
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
