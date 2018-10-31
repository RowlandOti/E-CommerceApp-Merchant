package com.rowland.delivery.remote.model.product


import com.rowland.delivery.remote.model.FirestoreModel
import java.util.*

class ProductPayload : FirestoreModel {

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

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }
}