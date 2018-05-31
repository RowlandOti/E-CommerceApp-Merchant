package com.rowland.delivery.features.dash.domain.models.category

import com.google.firebase.firestore.Exclude
import com.rowland.delivery.features.dash.domain.models.FirestoreModel

/**
 * Created by Rowland on 5/5/2018.
 */

class Category : FirestoreModel {

    var name: String? = null

    @Exclude
    var firestoreUid: String? = null
        private set

    override fun <T : FirestoreModel> withFirestoreId(firestoreUid: String): T {
        this.firestoreUid = firestoreUid
        return this as T
    }

    constructor()

    constructor(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "CategoryScope{" +
                "name='" + name + '\''.toString() +
                '}'.toString()
    }
}
