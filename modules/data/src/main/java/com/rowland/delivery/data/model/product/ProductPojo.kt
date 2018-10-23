package com.rowland.delivery.data.model.product

import java.util.*


class ProductPojo {
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var price: Int? = null
    var itemCode: String? = null
    var merchantCode: String? = null
    var itemQuantity: Int? = null
    var unitsOfMeasure: Int? = null
    var saleQuantity: Int? = null
    var imageUrl: String? = null
    var imageUrls = ArrayList<String>()
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var deletedAt: Date? = null
    var firestoreUid: String? = null


    override fun toString(): String {
        return "ProductPojo(id=$id, name=$name, description=$description, price=$price, itemCode=$itemCode, merchantCode=$merchantCode, itemQuantity=$itemQuantity, unitsOfMeasure=$unitsOfMeasure, saleQuantity=$saleQuantity, imageUrl=$imageUrl, imageUrls=$imageUrls, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, firestoreUid=$firestoreUid)"
    }
}
