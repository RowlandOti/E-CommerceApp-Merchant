package com.rowland.delivery.presentation.model.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList
import java.util.Date

@Parcelize
class ProductModel : Parcelable {

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
        return "ProductModel(id=$id, name=$name, description=$description, price=$price, itemCode=$itemCode, merchantCode=$merchantCode, itemQuantity=$itemQuantity, unitsOfMeasure=$unitsOfMeasure, saleQuantity=$saleQuantity, imageUrl=$imageUrl, imageUrls=$imageUrls, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, firestoreUid=$firestoreUid)"
    }
}