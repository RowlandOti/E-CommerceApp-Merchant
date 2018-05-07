package com.rowland.delivery.features.dash.data.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.rowland.delivery.features.dash.domain.models.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by Rowland on 5/5/2018.
 */

public class CategoryRepository {

    private FirebaseFirestore mFirebaseFirestone;

    @Inject
    public CategoryRepository(FirebaseFirestore firebaseFirestone) {
        this.mFirebaseFirestone = firebaseFirestone;
    }

    public Flowable<List<Category>> getCategories(String userUID) {
        CollectionReference categoryCollectionRef = mFirebaseFirestone.collection("categories");
        Query query = categoryCollectionRef.whereEqualTo(String.format("members.%s", userUID), true);
        return RxFirestore.observeQueryRef(query, Category.class);
    }


    public Single<Category> createCategory(Category category, String userUID) {
        DocumentReference documentReference = mFirebaseFirestone.collection("categories").document(category.getName());
        documentReference.set(category, SetOptions.merge()).addOnSuccessListener(aVoid -> {
            Map<String, Object> members = new HashMap<>();
            members.put(String.format("members.%s", userUID), true);
            documentReference.update(members);
        });

        return RxFirestore.getDocument(documentReference, Category.class).toSingle();
    }
}
