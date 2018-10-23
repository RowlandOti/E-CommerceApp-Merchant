package com.rowland.delivery.data.model.order

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderItemPojo {

    var itemCode: String? = null
    var title: String? = null
    var imageUrl: String? = null
    var itemQuantity: Int? = null
    var price: Int? = null
    var itemTotal: Int? = null
    var foreign: Boolean? = null


    override fun toString(): String {
        return "OrderItemPojo(itemCode=$itemCode, title=$title, imageUrl=$imageUrl, itemQuantity=$itemQuantity, price=$price, itemTotal=$itemTotal, foreign=$foreign)"
    }
}
