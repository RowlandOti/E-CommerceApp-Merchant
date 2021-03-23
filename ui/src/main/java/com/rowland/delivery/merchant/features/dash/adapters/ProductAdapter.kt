/*
 * Copyright 2021 Otieno Rowland
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.merchant.R
import com.rowland.delivery.merchant.databinding.RowSingleProductBinding
import com.rowland.delivery.merchant.features.dash.tools.recylerview.HFRecyclerView
import com.rowland.delivery.presentation.model.product.ProductModel
import com.rowland.delivery.presentation.viewmodels.product.ProductEvent

/**
 * Created by Rowland on 5/13/2018.
 */

class ProductAdapter(data: List<ProductModel>?, withHeader: Boolean, withFooter: Boolean) :
    HFRecyclerView<ProductModel>(data, withHeader, withFooter) {

    private var viewBinderHelper: ViewBinderHelper = ViewBinderHelper()
    private lateinit var actionListener: ProductActionListener

    interface ProductActionListener {

        fun onProductActionListener(event: ProductEvent, fn: () -> Unit)
    }

    constructor() : this(false, false) {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    constructor(withHeader: Boolean, withFooter: Boolean) : this(null, withHeader, withFooter)

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = RowSingleProductBinding
            .inflate(inflater, parent, false)
        return ProductViewHolder(binding)
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
                    return this@ProductAdapter.data[oldItemPosition].firestoreUid === productEntities[newItemPosition]
                        .firestoreUid
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

    inner class ProductViewHolder(private val itemViewBinding: RowSingleProductBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(productModel: ProductModel) {
            FirebaseStorage.getInstance().reference.child(productModel.imageUrls[productModel.imageUrls.size - 1])
                .downloadUrl
                .addOnSuccessListener { uri ->
                    val options = RequestOptions()
                    options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)

                    Glide.with(itemViewBinding.contentSingleProduct.productImageview.context)
                        .load(uri.toString())
                        .apply(options)
                        .listener(
                            GlidePalette.with(uri.toString())
                                .use(BitmapPalette.Profile.VIBRANT_LIGHT)
                                .intoBackground(itemViewBinding.contentSingleProduct.cardMainContent)
                                .use(BitmapPalette.Profile.VIBRANT_DARK)
                                .intoTextColor(
                                    itemViewBinding.contentSingleProduct.productDescription,
                                    BitmapPalette.Swatch.BODY_TEXT_COLOR
                                )
                                .crossfade(true)
                        )
                        .into(itemViewBinding.contentSingleProduct.productImageview)
                }

            itemViewBinding.contentSingleProduct.productNameTextview.text = productModel.name
            itemViewBinding.contentSingleProduct.productDescription.text = productModel.description
            itemViewBinding.contentSingleProduct.productPrice.apply {
                text = this.context.getString(
                    R.string.ksh_label_with_value_num, productModel.price!!
                )
            }

            viewBinderHelper.bind(itemViewBinding.swipeLayout, productModel.firestoreUid)

            itemViewBinding.contentSingleProductReveal.btnUnpublish.apply {
                setOnClickListener {
                    this.setIndeterminate()
                    actionListener.onProductActionListener(ProductEvent.UnPublish) { this.setFinish() }
                    viewBinderHelper.closeLayout(productModel.firestoreUid)
                }
            }

            itemViewBinding.contentSingleProductReveal.btnEdit.apply {
                setOnClickListener {
                    this.setIndeterminate()
                    actionListener.onProductActionListener(ProductEvent.Edit(productModel)) { this.setFinish() }
                    viewBinderHelper.closeLayout(productModel.firestoreUid)
                }
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
