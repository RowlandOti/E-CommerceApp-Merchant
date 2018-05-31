package com.rowland.delivery.features.dash.domain.models.product

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.features.dash.domain.models.FirestoreModel
import java.util.*

/**
 * Created by Rowland on 5/13/2018.
 */

class Product : FirestoreModel {

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
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var deletedAt: Date? = null

    @Exclude
    var firestoreUid: String? = null

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }
}
