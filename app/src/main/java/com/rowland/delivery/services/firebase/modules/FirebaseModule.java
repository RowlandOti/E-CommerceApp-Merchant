package com.rowland.delivery.services.firebase.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/6/2018.
 */

@Module
public class FirebaseModule {

    @Provides
    public FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseFirestore providesFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
