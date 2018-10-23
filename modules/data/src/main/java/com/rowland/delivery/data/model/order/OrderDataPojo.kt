package com.rowland.delivery.data.model.order


class OrderDataPojo {

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
    var items: List<OrderItemPojo>? = null
    var deliveryFee: Int? = null
    var orderItemsQuantity: Int? = null
    var orderSubTotal: String? = null
    var orderTotal: Int? = null
    var firestoreUid: String? = null

    override fun toString(): String {
        return "OrderDataPojo{" +
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
