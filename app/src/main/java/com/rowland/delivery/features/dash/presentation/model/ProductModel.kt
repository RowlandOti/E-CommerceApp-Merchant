package com.rowland.delivery.features.dash.presentation.model

import java.util.*

class ProductModel {

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
}