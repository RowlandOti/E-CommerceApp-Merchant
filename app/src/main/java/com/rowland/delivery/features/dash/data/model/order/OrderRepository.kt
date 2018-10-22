package com.rowland.delivery.features.dash.data.model.order

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rowland.delivery.features.dash.domain.contracts.IOrderRepository
import com.rowland.delivery.features.dash.domain.models.order.OrderData
import com.rowland.delivery.features.dash.presentation.tools.snapshots.DocumentWithIdSnapshotMapper
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderRepository @Inject
constructor(private val mFirebaseFirestone: FirebaseFirestore) : IOrderRepository {

    /*public Flowable<List<OrderData>> getOrders(String userUID) {
        CollectionReference categoryCollectionRef = mFirebaseFirestone.collection("orders");
        Query query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUID), true);
        return RxFirestore.observeQueryRef(query, OrderData.class);
    }*/

    override fun getOrders(userUID: String): Flowable<List<OrderData>> {
        val orderCollectionRef = mFirebaseFirestone.collection("orderdata")
        return RxFirestore.observeQueryRef(orderCollectionRef, DocumentWithIdSnapshotMapper.listOf(OrderData::class.java) as io.reactivex.functions.Function<QuerySnapshot, List<OrderData>>)
    }

    override fun updateOrderStatus(status: String, firestoreUid: String): Completable {
        val orderDocumentRef = mFirebaseFirestone.collection("orderdata").document(firestoreUid)
        val orderUpdate = HashMap<String, Any>()
        orderUpdate["status"] = status
        return RxFirestore.updateDocument(orderDocumentRef, orderUpdate)
    }
}
