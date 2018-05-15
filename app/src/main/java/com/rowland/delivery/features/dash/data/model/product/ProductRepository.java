package com.rowland.delivery.features.dash.data.model.product;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.rowland.delivery.features.dash.domain.models.product.Product;
import com.rowland.delivery.features.dash.presentation.tools.snapshots.DocumentWithIdSnapshotMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Rowland on 5/13/2018.
 */

public class ProductRepository {

    private FirebaseFirestore mFirebaseFirestone;

    @Inject
    public ProductRepository(FirebaseFirestore firebaseFirestone) {
        this.mFirebaseFirestone = firebaseFirestone;
    }

    public Flowable<List<Product>> getProducts(String userUID) {
        CollectionReference productCollectionRef = mFirebaseFirestone.collection("products");
        return RxFirestore.observeQueryRef(productCollectionRef, (io.reactivex.functions.Function) DocumentWithIdSnapshotMapper.listOf(Product.class));
    }

    public Single<Product> createPoduct(Product product, String userUid) {
        DocumentReference documentReference = mFirebaseFirestone.collection("products").document();
        documentReference.set(product, SetOptions.merge()).addOnSuccessListener(aVoid -> {
            Map<String, Object> members = new HashMap<>();
            members.put(String.format("merchants.%s", userUid), true);
            documentReference.update(members);
        });

        return RxFirestore.getDocument(documentReference, Product.class).toSingle();
    }


}
