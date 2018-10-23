package com.rowland.delivery.features.dash.presentation.adapters

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.features.dash.presentation.model.ProductModel
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductEvent
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.content_single_product.view.*
import kotlinx.android.synthetic.main.content_single_product_reveal.view.*
import kotlinx.android.synthetic.main.row_single_product.view.*


/**
 * Created by Rowland on 5/13/2018.
 */

class ProductAdapter(data: List<ProductModel>?, withHeader: Boolean, withFooter: Boolean) : HFRecyclerView<ProductModel>(data, withHeader, withFooter) {

    private lateinit var viewBinderHelper: ViewBinderHelper
    private lateinit var actionListener: ProductActionListener

    interface ProductActionListener {
        fun onProductActionListener(event: ProductEvent, fn: () -> Unit)
    }

    constructor() : this(false, false) {
        viewBinderHelper = ViewBinderHelper()
        viewBinderHelper.setOpenOnlyOne(true)
    }

    constructor(withHeader: Boolean, withFooter: Boolean) : this(null, withHeader, withFooter)

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ProductViewHolder(inflater.inflate(R.layout.row_single_product, parent, false))
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val data = getItem(position)
            holder.bind(data)

        } else if (holder is HeaderViewHolder) {

        } else if (holder is FooterViewHolder) {

        }
    }

    fun setActionListener(actionListener: ProductActionListener) {
        this.actionListener = actionListener
    }

    fun setList(productEntities: List<ProductModel>) {
        if (this.data == null) {
            this.data = productEntities
            notifyItemRangeInserted(0, productEntities.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ProductAdapter.data.size
                }

                override fun getNewListSize(): Int {
                    return productEntities.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ProductAdapter.data[oldItemPosition].firestoreUid === productEntities[newItemPosition].firestoreUid
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = productEntities[newItemPosition]
                    val oldItem = productEntities[oldItemPosition]
                    return newItem.firestoreUid === oldItem.firestoreUid && newItem == oldItem
                }
            })
            this.data = productEntities
            result.dispatchUpdatesTo(this)
        }
    }

    fun saveStates(outState: Bundle) {
        viewBinderHelper.saveStates(outState)
    }

    fun restoreStates(inState: Bundle) {
        viewBinderHelper.restoreStates(inState)
    }

    inner class ProductViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(ProductModel: ProductModel) {
            FirebaseStorage.getInstance().reference.child(ProductModel.imageUrls.get(ProductModel.imageUrls.size -1)).downloadUrl
                    .addOnSuccessListener { uri ->
                        val options = RequestOptions()
                        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)

                        Glide.with(itemView.product_imageview!!.context)
                                .load(uri.toString())
                                .apply(options)
                                .listener(GlidePalette.with(uri.toString())
                                        .use(BitmapPalette.Profile.VIBRANT_LIGHT)
                                        .intoBackground(itemView.card_main_content)
                                        .use(BitmapPalette.Profile.VIBRANT_DARK)
                                        .intoTextColor(itemView.product_description, BitmapPalette.Swatch.BODY_TEXT_COLOR)
                                        .crossfade(true))
                                .into(itemView.product_imageview!!)
                    }

            itemView.product_name_textview!!.text = ProductModel.name
            itemView.product_description!!.text = ProductModel.description
            itemView.product_price!!.text = "Ksh " + ProductModel.price!!

            viewBinderHelper.bind(itemView.swipe_layout!!, ProductModel.firestoreUid)

            itemView.btn_unpublish!!.setOnClickListener { v ->
                itemView.btn_unpublish!!.setIndeterminate()
                actionListener.onProductActionListener(ProductEvent.Unpublish(), { itemView.btn_unpublish.setFinish() })
                viewBinderHelper.closeLayout(ProductModel.firestoreUid)
            }

            itemView.btn_edit!!.setOnClickListener { v ->
                itemView.btn_edit!!.setIndeterminate()
                actionListener.onProductActionListener(ProductEvent.Edit(), { itemView.btn_edit.setFinish() })
                viewBinderHelper.closeLayout(ProductModel.firestoreUid)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
