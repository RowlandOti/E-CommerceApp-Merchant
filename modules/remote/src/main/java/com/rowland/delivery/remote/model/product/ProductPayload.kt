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

package com.rowland.delivery.remote.model.product

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.remote.model.FirestoreModel
import java.util.ArrayList
import java.util.Date

class ProductPayload : FirestoreModel {

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

    @Exclude
    var firestoreUid: String? = null

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }
}
