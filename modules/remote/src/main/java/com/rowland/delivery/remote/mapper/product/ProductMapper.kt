package com.rowland.delivery.remote.mapper.product

import com.rowland.delivery.data.model.product.ProductPojo
import com.rowland.delivery.remote.mapper.IMapper
import com.rowland.delivery.remote.model.product.ProductPayload
import javax.inject.Inject

/**
 * Map a [ProductPayload] to and from a [ProductPojo] instance when data is moving between
 * this later and the Data layer
 */
open class ProductMapper @Inject constructor() : IMapper<ProductPayload, ProductPojo> {

    /**
     * Map an instance of a [ProductPayload] to a [ProductPojo] model
     */
    override fun mapFromRemote(type: ProductPayload): ProductPojo {
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
        product.imageUrls - type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }

    /**
     * Map an instance of a [ProductPojo] to a [ProductPayload] model
     */
    override fun mapToRemote(type: ProductPojo): ProductPayload {
        val product = ProductPayload()
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
        product.imageUrls - type.imageUrls
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.deletedAt = type.deletedAt
        product.firestoreUid = type.firestoreUid
        return product
    }
}