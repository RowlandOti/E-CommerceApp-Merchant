package com.rowland.delivery.features.dash.domain.models.order

import java.util.*

/**
 * Created by Rowland on 5/8/2018.
 */

class Order {

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
    val items: List<OrderItem>? = null
    val deliveryFee: Int? = null
    val orderItemsQuantity: Int? = null
    val orderSubTotal: String? = null
    val orderTotal: Int? = null
}
