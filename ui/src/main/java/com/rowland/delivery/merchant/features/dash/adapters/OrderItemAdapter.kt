package com.rowland.delivery.merchant.features.dash.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.features.dash.tools.recylerview.HFRecyclerView
import com.rowland.delivery.presentation.model.order.OrderItemModel
import kotlinx.android.synthetic.main.row_single_order_item.view.*


/**
 * Created by Rowland on 5/9/2018.
 */

class OrderItemAdapter : HFRecyclerView<OrderItemModel> {

    constructor() : super(null, false, false) {}

    constructor(withHeader: Boolean, withFooter: Boolean) : super(null, withHeader, withFooter) {}

    constructor(data: List<OrderItemModel>, withHeader: Boolean, withFooter: Boolean) : super(data, withHeader, withFooter) {}

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return OrderItemViewHolder(inflater.inflate(R.layout.row_single_order_item, parent, false))
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
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

    class OrderItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {



        fun bind(orderItem: OrderItemModel) {
            Glide.with(itemView.food_image!!.context).load(orderItem.imageUrl).into(itemView.food_image!!)
            itemView.items_count!!.text = orderItem.itemQuantity.toString()
            itemView.item_name!!.text = orderItem.title
            itemView.item_price!!.text = "Price: Ksh " + orderItem.price + " each"
            itemView.item_total_price!!.text = "Total price: Ksh " + orderItem.itemTotal!!
        }
    }

    class HeaderViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
