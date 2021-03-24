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

package com.rowland.delivery.remote.firebasesnapshots

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.rowland.delivery.remote.model.FirestoreModel
import io.reactivex.functions.Function
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by Rowland on 5/12/2018.
 */

abstract class FirestoreDocumentWithIdSnapshotMapper<T, U> private constructor() : Function<T, U> {

    class TypedMapQuerySnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) :
        FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, LinkedHashMap<String, U>>() {

        override fun apply(querySnapshot: QuerySnapshot): LinkedHashMap<String, U> {
            val items = LinkedHashMap<String, U>()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.put(
                    documentSnapshot.id,
                    documentSnapshot.toObject(this.clazz)!!.withFirestoreId<U>(documentSnapshot.id)
                )
            }

            return items
        }
    }

    class TypedListQuerySnapshotMapper<U : FirestoreModel> @JvmOverloads internal constructor(
        private val clazz: Class<U>,
        private val mapper: Function<DocumentSnapshot, U>? = null
    ) : FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>>() {

        @Throws(Exception::class)
        override fun apply(querySnapshot: QuerySnapshot): List<U> {
            val items = ArrayList<U>()
            val var3 = querySnapshot.iterator()

            while (var3.hasNext()) {
                val documentSnapshot = var3.next() as DocumentSnapshot
                items.add(
                    if (this.mapper != null) this.mapper.apply(documentSnapshot) else documentSnapshot.toObject(
                        this.clazz
                    )!!.withFirestoreId(documentSnapshot.id)
                )
            }
            return items
        }
    }

    class TypedDocumentSnapshotMapper<U : FirestoreModel> internal constructor(private val clazz: Class<U>) :
        FirestoreDocumentWithIdSnapshotMapper<DocumentSnapshot, U>() {

        override fun apply(documentSnapshot: DocumentSnapshot): U {
            return documentSnapshot.toObject(this.clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }

    companion object {

        fun <U : FirestoreModel> of(clazz: Class<U>): FirestoreDocumentWithIdSnapshotMapper<DocumentSnapshot, U> {
            return TypedDocumentSnapshotMapper(clazz)
        }

        fun <U : FirestoreModel> listOf(clazz: Class<U>):
            FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
                return TypedListQuerySnapshotMapper(clazz)
            }

        fun <U : FirestoreModel> listOf(
            clazz: Class<U>,
            mapper: Function<DocumentSnapshot, U>
        ): FirestoreDocumentWithIdSnapshotMapper<QuerySnapshot, List<U>> {
            return TypedListQuerySnapshotMapper(clazz, mapper)
        }

        fun <U : FirestoreModel> mapOf(clazz: Class<U>):
            FirestoreDocumentWithIdSnapshotMapper.TypedMapQuerySnapshotMapper<U> {
                return TypedMapQuerySnapshotMapper(clazz)
            }

        private fun <U : FirestoreModel> getDataSnapshotTypedValue(
            documentSnapshot: DocumentSnapshot,
            clazz: Class<U>
        ): U {
            return documentSnapshot.toObject(clazz)!!.withFirestoreId(documentSnapshot.id)
        }
    }
}
