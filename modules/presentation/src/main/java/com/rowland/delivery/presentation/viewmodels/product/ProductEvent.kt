package com.rowland.delivery.presentation.viewmodels.product

import com.rowland.delivery.presentation.model.product.ProductModel

sealed class ProductEvent {
    class Edit(val productModel: ProductModel) : ProductEvent()
    object UnPublish : ProductEvent()
}