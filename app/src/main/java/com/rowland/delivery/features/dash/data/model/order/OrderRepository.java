package com.rowland.delivery.features.dash.data.model.order;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.presentation.tools.snapshots.DocumentWithIdSnapshotMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Rowland on 5/8/2018.
 */

public class OrderRepository {

    private FirebaseFirestore mFirebaseFirestone;

    @Inject
    public OrderRepository(FirebaseFirestore firebaseFirestone) {
        this.mFirebaseFirestone = firebaseFirestone;
    }

    /*public Flowable<List<OrderData>> getOrders(String userUID) {
        CollectionReference categoryCollectionRef = mFirebaseFirestone.collection("orders");
        Query query = categoryCollectionRef.whereEqualTo(String.format("merchants.%s", userUID), true);
        return RxFirestore.observeQueryRef(query, OrderData.class);
    }*/

    public Flowable<List<OrderData>> getOrders(String userUID) {
        CollectionReference orderCollectionRef = mFirebaseFirestone.collection("orderdata");
        return RxFirestore.observeQueryRef(orderCollectionRef, (io.reactivex.functions.Function) DocumentWithIdSnapshotMapper.listOf(OrderData.class));
    }

    public Completable updateOrderStatus(String status, String firestoreUid) {
        DocumentReference orderDocumentRef = mFirebaseFirestone.collection("orderdata").document(firestoreUid);
        Map<String, Object> orderUpdate = new HashMap<>();
        orderUpdate.put("status", status);
        return RxFirestore.updateDocument(orderDocumentRef, orderUpdate);
    }
}
