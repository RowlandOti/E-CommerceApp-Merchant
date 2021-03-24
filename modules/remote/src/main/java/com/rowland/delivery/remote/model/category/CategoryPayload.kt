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

package com.rowland.delivery.remote.model.category

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.remote.model.FirestoreModel

class CategoryPayload : FirestoreModel {

    lateinit var name: String
    var merchants = HashMap<String, Boolean>()

    @Exclude
    var firestoreUid: String? = null

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }

    override fun toString(): String {
        return "CategoryPayload(name=$name, merchants=$merchants, firestoreUid=$firestoreUid)"
    }
}
