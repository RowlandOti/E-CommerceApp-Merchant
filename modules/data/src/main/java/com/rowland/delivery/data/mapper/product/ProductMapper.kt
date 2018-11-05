package com.rowland.delivery.data.mapper.product

import com.rowland.delivery.data.mapper.Mapper
import com.rowland.delivery.data.model.product.ProductPojo
import com.rowland.delivery.domain.models.product.ProductEntity
import javax.inject.Inject

open class ProductMapper @Inject constructor() : Mapper<ProductPojo, ProductEntity> {

    override fun mapFromData(type: ProductPojo): ProductEntity {
        val product = ProductEntity()
        product.id = type.id
        product.name = type.name
        product.description = type.description
        product.price = type.price
        product.itemCode = type.itemCode
        product.merchantCode = type.merchantCode
        product.itemQuantity = type.itemQuantity
        product.unitsOfMeasure = type.unitsOfMeasure
        product.saleQuantity = type.saleQuantity
        product.imageUrl = type.imageUrl
        product.imageUrls = type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }

    override fun mapToData(type: ProductEntity): ProductPojo {
        val product = ProductPojo()
        product.id = type.id
        product.name = type.name
        product.description = type.description
        product.price = type.price
        product.itemCode = type.itemCode
        product.merchantCode = type.merchantCode
        product.itemQuantity = type.itemQuantity
        product.unitsOfMeasure = type.unitsOfMeasure
        product.saleQuantity = type.saleQuantity
        product.imageUrl = type.imageUrl
        product.imageUrls = type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }
}