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

package com.rowland.delivery.remote.source.order

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rowland.delivery.data.contracts.order.IOrderRemoteSource
import com.rowland.delivery.data.model.order.OrderDataPojo
import com.rowland.delivery.remote.firebasesnapshots.FirestoreDocumentWithIdSnapshotMapper
import com.rowland.delivery.remote.mapper.order.OrderDataMapper
import com.rowland.delivery.remote.model.order.OrderDataPayload
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.HashMap
import javax.inject.Inject

class OrderRemoteSource @Inject constructor(
    private val mFirebaseFirestone: FirebaseFirestore,
    private val mapper: OrderDataMapper
) : IOrderRemoteSource {

    override fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable {
        val orderDocumentRef = mFirebaseFirestone.collection("orderdata").document(orderUid)
        return RxFirestore.updateDocument(orderDocumentRef, orderUpdateFields)
    }

    override fun deleteOrder(orderUid: String): Completable {
        val documentReference = mFirebaseFirestone.collection("orderdata").document(orderUid)
        return RxFirestore.deleteDocument(documentReference)
    }

    override fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>> {
        val orderCollectionRef = mFirebaseFirestone.collection("orderdata")
        return RxFirestore.observeQueryRef(
            orderCollectionRef,
            FirestoreDocumentWithIdSnapshotMapper.listOf(OrderDataPayload::class.java) as
                io.reactivex.functions.Function<QuerySnapshot, List<OrderDataPayload>>
        )
            .map { it }
            .map {
                val orderDataPojos = mutableListOf<OrderDataPojo>()
                it.map { orderDataPojos.add(mapper.mapFromRemote(it)) }
                orderDataPojos
            }
    }
}
