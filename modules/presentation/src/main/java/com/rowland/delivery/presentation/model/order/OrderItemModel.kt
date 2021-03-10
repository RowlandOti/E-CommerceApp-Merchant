package com.rowland.delivery.presentation.model.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Rowland on 5/8/2018.
 */

@Parcelize
class OrderItemModel: Parcelable {

    var itemCode: String? = null
    var title: String? = null
    var imageUrl: String? = null
    var itemQuantity: Int? = null
    var price: Int? = null
    var itemTotal: Int? = null
    var foreign: Boolean? = null

    override fun toString(): String {
        return "OrderItemModel(itemCode=$itemCode, title=$title, imageUrl=$imageUrl, itemQuantity=$itemQuantity, price=$price, itemTotal=$itemTotal, foreign=$foreign)"
    }
}
