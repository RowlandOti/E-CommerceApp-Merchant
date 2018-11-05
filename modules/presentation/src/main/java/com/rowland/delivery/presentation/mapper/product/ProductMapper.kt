package com.rowland.delivery.presentation.mapper.product

import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.presentation.mapper.IMapper
import com.rowland.delivery.presentation.model.product.ProductModel
import javax.inject.Inject

open class ProductMapper @Inject constructor() : IMapper<ProductModel, ProductEntity> {
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
        productModel.imageUrls = type.imageUrls
        productModel.createdAt = type.createdAt
        productModel.updatedAt = type.updatedAt
        productModel.deletedAt = type.deletedAt
        productModel.firestoreUid = type.firestoreUid
        return productModel
    }
}