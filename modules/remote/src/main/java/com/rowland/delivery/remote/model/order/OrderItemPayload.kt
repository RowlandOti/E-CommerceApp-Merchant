package com.rowland.delivery.remote.model.order

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderItemPayload {

    var itemCode: String? = null
    var title: String? = null
    var imageUrl: String? = null
    var itemQuantity: Int? = null
    var price: Int? = null
    var itemTotal: Int? = null
    var foreign: Boolean? = null
}
