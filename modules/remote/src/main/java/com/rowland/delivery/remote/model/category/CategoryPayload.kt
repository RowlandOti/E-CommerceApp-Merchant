package com.rowland.delivery.remote.model.category

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.remote.model.FirestoreModel


class CategoryPayload : FirestoreModel {

    var name: String? = null
    var merchants = HashMap<String, Boolean>()


    @Exclude
    var firestoreUid: String? = null


    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }

    override fun toString(): String {
        return "CategoryPayload(name=$name, merchants=$merchants, firestoreUid=$firestoreUid)"
    }
}

