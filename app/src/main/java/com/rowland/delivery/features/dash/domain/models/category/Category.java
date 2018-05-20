package com.rowland.delivery.features.dash.domain.models.category;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.rowland.delivery.features.dash.domain.models.FirestoreModel;

/**
 * Created by Rowland on 5/5/2018.
 */

public class Category implements FirestoreModel {

    private String name;

    @Exclude
    private String firestoreUid;

    @Override
    public <T extends FirestoreModel> T withFirestoreId(@NonNull String firestoreUid) {
        this.firestoreUid = firestoreUid;
        return (T) this;
    }

    public String getFirestoreUid() {
        return firestoreUid;
    }

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryScope{" +
                "name='" + name + '\'' +
                '}';
    }
}
