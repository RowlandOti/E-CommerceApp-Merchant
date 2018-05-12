package com.rowland.delivery.features.dash.domain.models;

import android.support.annotation.NonNull;

/**
 * Created by Rowland on 5/12/2018.
 */

public interface FirestoreModel {

   <T extends FirestoreModel> T withFirestoreId(@NonNull final String firestoreUid);
}
