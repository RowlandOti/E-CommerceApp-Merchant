package com.rowland.delivery.features.dash.domain.models.order

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.features.dash.domain.models.FirestoreModel

/**
 * Created by Rowland on 5/9/2018.
 */

class OrderData : FirestoreModel {

    var id: Int? = null
    var orderRef: String? = null
    var status: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var itemIds: String? = null
    var phone: String? = null
    var name: String? = null
    var address: String? = null
    var lng: String? = null
    var lat: String? = null
    var orderDescription: String? = null
    var items: List<OrderItem>? = null
    var deliveryFee: Int? = null
    var orderItemsQuantity: Int? = null
    var orderSubTotal: String? = null
    var orderTotal: Int? = null

    @Exclude
    var firestoreUid: String? = null
        private set

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }

    override fun toString(): String {
        return "OrderData{" +
                "id=" + id +
                ", orderRef='" + orderRef + '\''.toString() +
                ", status='" + status + '\''.toString() +
                ", createdAt='" + createdAt + '\''.toString() +
                ", updatedAt='" + updatedAt + '\''.toString() +
                ", itemIds='" + itemIds + '\''.toString() +
                ", phone='" + phone + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", address='" + address + '\''.toString() +
                ", lng='" + lng + '\''.toString() +
                ", lat='" + lat + '\''.toString() +
                ", orderDescription='" + orderDescription + '\''.toString() +
                ", items=" + items +
                ", deliveryFee=" + deliveryFee +
                ", orderItemsQuantity=" + orderItemsQuantity +
                ", orderSubTotal='" + orderSubTotal + '\''.toString() +
                ", orderTotal=" + orderTotal +
                ", firestoreUid='" + firestoreUid + '\''.toString() +
                '}'.toString()
    }
}
