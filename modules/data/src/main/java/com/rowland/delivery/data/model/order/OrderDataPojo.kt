/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
