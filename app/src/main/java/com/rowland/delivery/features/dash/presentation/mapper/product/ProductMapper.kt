package com.rowland.delivery.features.dash.presentation.mapper.product

import com.rowland.delivery.features.dash.domain.models.product.ProductEntity
import com.rowland.delivery.features.dash.presentation.mapper.Mapper
import com.rowland.delivery.features.dash.presentation.model.ProductModel
import javax.inject.Inject

open class ProductMapper @Inject constructor() : Mapper<ProductModel, ProductEntity> {
    override fun mapToView(type: ProductEntity): ProductModel {
        val productModel = ProductModel()
        productModel.id = type.id
        productModel.name = type.name
        productModel.description = type.description
        productModel.price = type.price
        productModel.itemCode = type.itemCode
        productModel.merchantCode = type.merchantCode
        productModel.itemQuantity = type.itemQuantity
        productModel.unitsOfMeasure = type.unitsOfMeasure
        productModel.saleQuantity = type.saleQuantity
        productModel.imageUrl = type.imageUrl
        productModel.imageUrls - type.imageUrls
        productModel.createdAt = type.createdAt
        productModel.updatedAt = type.updatedAt
        productModel.deletedAt = type.deletedAt
        productModel.firestoreUid = type.firestoreUid
        return productModel
    }
}