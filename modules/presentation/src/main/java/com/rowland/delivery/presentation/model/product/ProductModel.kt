/*
 * Copyright 2021 Otieno Rowland
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

package com.rowland.delivery.presentation.model.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList
import java.util.Date

@Parcelize
class ProductModel : Parcelable {

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

    override fun toString(): String {
        return "ProductModel(id=$id, name=$name, description=$description, price=$price, itemCode=$itemCode, " +
            "merchantCode=$merchantCode, itemQuantity=$itemQuantity, unitsOfMeasure=$unitsOfMeasure, " +
            "saleQuantity=$saleQuantity, imageUrl=$imageUrl, imageUrls=$imageUrls, createdAt=$createdAt, " +
            "updatedAt=$updatedAt, deletedAt=$deletedAt, firestoreUid=$firestoreUid)"
    }
}
