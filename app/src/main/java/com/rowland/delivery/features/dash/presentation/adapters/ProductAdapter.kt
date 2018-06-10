package com.rowland.delivery.features.dash.presentation.adapters

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
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
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.presentation.tools.recylerview.HFRecyclerView
import com.rowland.delivery.features.dash.presentation.viewmodels.product.ProductEvent
import com.rowland.delivery.merchant.R
import kotlinx.android.synthetic.main.content_single_product.view.*
import kotlinx.android.synthetic.main.content_single_product_reveal.view.*
import kotlinx.android.synthetic.main.row_single_product.view.*


/**
 * Created by Rowland on 5/13/2018.
 */

class ProductAdapter(data: List<Product>?, withHeader: Boolean, withFooter: Boolean) : HFRecyclerView<Product>(data, withHeader, withFooter) {

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

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(inflater.inflate(R.layout.row_single_product, parent, false))
    }

    override fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

    fun setList(products: List<Product>) {
        if (this.data == null) {
            this.data = products
            notifyItemRangeInserted(0, products.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ProductAdapter.data.size
                }

                override fun getNewListSize(): Int {
                    return products.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ProductAdapter.data[oldItemPosition].firestoreUid === products[newItemPosition].firestoreUid
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = products[newItemPosition]
                    val oldItem = products[oldItemPosition]
                    return newItem.firestoreUid === oldItem.firestoreUid && newItem == oldItem
                }
            })
            this.data = products
            result.dispatchUpdatesTo(this)
        }
    }

    fun saveStates(outState: Bundle) {
        viewBinderHelper.saveStates(outState)
    }

    fun restoreStates(inState: Bundle) {
        viewBinderHelper.restoreStates(inState)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(product: Product) {
            FirebaseStorage.getInstance().reference.child(product.imageUrls.get(0)).downloadUrl
                    .addOnSuccessListener { uri ->
                        val options = RequestOptions()
                        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)

                        Glide.with(itemView.product_imageview!!.context)
                                .load(uri.toString())
                                .apply(options)
                                .listener(GlidePalette.with(uri.toString())
                                        .use(BitmapPalette.Profile.MUTED_DARK)
                                        .intoBackground(itemView.card_main_content)
                                        //.intoTextColor(textView)
                                        .use(BitmapPalette.Profile.VIBRANT)
                                        //.intoBackground(titleView, GlidePalette.Swatch.RGB)
                                        .intoTextColor(itemView.product_description, BitmapPalette.Swatch.BODY_TEXT_COLOR)
                                        .crossfade(true))
                                .into(itemView.product_imageview!!)
                    }

            itemView.product_name_textview!!.text = product.name
            itemView.product_description!!.text = product.description
            itemView.product_price!!.text = "Ksh " + product.price!!

            viewBinderHelper.bind(itemView.swipe_layout!!, product.firestoreUid)

            itemView.btn_unpublish!!.setOnClickListener { v ->
                itemView.btn_unpublish!!.setIndeterminate()
                actionListener.onProductActionListener(ProductEvent.Unpublish(), { itemView.btn_unpublish.setFinish() })
                viewBinderHelper.closeLayout(product.firestoreUid)
            }

            itemView.btn_edit!!.setOnClickListener { v ->
                itemView.btn_edit!!.setIndeterminate()
                actionListener.onProductActionListener(ProductEvent.Edit(), { itemView.btn_edit.setFinish() })
                viewBinderHelper.closeLayout(product.firestoreUid)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
