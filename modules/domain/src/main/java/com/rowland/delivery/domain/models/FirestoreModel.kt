package com.rowland.delivery.domain.models

/**
 * Created by Rowland on 5/12/2018.
 */

interface FirestoreModel {

    fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T
}
