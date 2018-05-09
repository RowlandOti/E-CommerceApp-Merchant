package com.rowland.delivery.features.dash.data.model.order;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.domain.models.order.OrderData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
        Query query = categoryCollectionRef.whereEqualTo(String.format("members.%s", userUID), true);
        return RxFirestore.observeQueryRef(query, OrderData.class);
    }*/

    public Flowable<List<OrderData>> getOrders(String userUID) {
        CollectionReference orderCollectionRef = mFirebaseFirestone.collection("orderdata");
        //return RxFirestore.observeQueryRef(orderCollectionRef, OrderData.class);

        List<OrderData> dataList = new ArrayList<>();
        orderCollectionRef.addSnapshotListener((queryDocumentSnapshots, e) -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                OrderData data = doc.toObject(OrderData.class);
                Log.d(OrderRepository.class.getSimpleName(), "OrderData: " + data.toString());
                dataList.add(data);
            }
        });
        return Flowable.just(dataList);
    }
}
