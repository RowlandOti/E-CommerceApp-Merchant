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

package com.rowland.delivery.remote.source.product

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.rowland.delivery.data.contracts.product.IProductRemoteSource
import com.rowland.delivery.data.model.product.ProductPojo
import com.rowland.delivery.remote.firebasesnapshots.FirestoreDocumentWithIdSnapshotMapper
import com.rowland.delivery.remote.mapper.product.ProductMapper
import com.rowland.delivery.remote.model.product.ProductPayload
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

class ProductRemoteSource @Inject constructor(
    private val mFirebaseFirestone: FirebaseFirestore,
    private val mapper: ProductMapper
) : IProductRemoteSource {

    override fun createProduct(
        productPojo: ProductPojo,
        productCategory: String,
        userUid: String
    ): Single<ProductPojo> {
        val documentReference = mFirebaseFirestone.collection("products").document()
        documentReference.set(mapper.mapToRemote(productPojo), SetOptions.merge()).addOnSuccessListener { aVoid ->
            val members = HashMap<String, Any>()
            members[String.format("merchants.%s", userUid)] = true
            members[String.format("categories.%s", productCategory)] = true
            documentReference.update(members)
        }
        return RxFirestore.getDocument(documentReference, ProductPayload::class.java).toSingle()
            .map { mapper.mapFromRemote(it) }
    }

    override fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable {
        val documentReference = mFirebaseFirestone.collection("products").document(productUid!!)
        val imageUrls = productUpdateFields.remove("imageUrls")
        if (imageUrls != null) {
            return RxFirestore.updateDocument(documentReference, productUpdateFields).doOnComplete {
                for (i in imageUrls as ArrayList<*>) {
                    documentReference.update("imageUrls", FieldValue.arrayUnion(i))
                }
            }
        }
        return RxFirestore.updateDocument(documentReference, productUpdateFields)
    }

    override fun deleteProduct(productUid: String): Completable {
        val documentReference = mFirebaseFirestone.collection("products").document(productUid)
        return RxFirestore.deleteDocument(documentReference)
    }

    override fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>> {
        val categoryCollectionRef = mFirebaseFirestone.collection("products")
        val query = categoryCollectionRef
            .whereEqualTo(String.format("merchants.%s", userUid), true)
            .whereEqualTo(String.format("categories.%s", productCategory), true)

        return RxFirestore.observeQueryRef(
            query,
            FirestoreDocumentWithIdSnapshotMapper.listOf(ProductPayload::class.java) as
                io.reactivex.functions.Function<QuerySnapshot, List<ProductPayload>>
        )
            .map { it }
            .map {
                val productPojos = mutableListOf<ProductPojo>()
                it.map { productPojos.add(mapper.mapFromRemote(it)) }
                productPojos
            }
    }
}
