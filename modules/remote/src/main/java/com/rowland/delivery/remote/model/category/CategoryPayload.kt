package com.rowland.delivery.remote.model.category

import com.rowland.delivery.remote.model.FirestoreModel


class CategoryPayload : FirestoreModel {

    var name: String? = null


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

