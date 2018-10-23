package com.rowland.delivery.presentation.model.order

import java.util.*

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderModel {

    val id: Int? = null
    val orderRef: String? = null
    val status: String? = null
    val createdAt: Date? = null
    val updatedAt: Date? = null
    val itemIds: String? = null
    val phone: String? = null
    val name: String? = null
    val address: String? = null
    val lng: String? = null
    val lat: String? = null
    val orderDescription: String? = null
    val items: List<OrderItemModel>? = null
    val deliveryFee: Int? = null
    val orderItemsQuantity: Int? = null
    val orderSubTotal: String? = null
    val orderTotal: Int? = null


    override fun toString(): String {
        return "OrderPojo(id=$id, orderRef=$orderRef, status=$status, createdAt=$createdAt, updatedAt=$updatedAt, itemIds=$itemIds, phone=$phone, name=$name, address=$address, lng=$lng, lat=$lat, orderDescription=$orderDescription, items=$items, deliveryFee=$deliveryFee, orderItemsQuantity=$orderItemsQuantity, orderSubTotal=$orderSubTotal, orderTotal=$orderTotal)"
    }
}
