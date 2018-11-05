package com.rowland.delivery.domain.models.category

import com.rowland.delivery.domain.models.FirestoreModel
import java.util.*

/**
 * Created by Rowland on 5/5/2018.
 */

class CategoryEntity : FirestoreModel {

    var name: String? = null
    var merchants = HashMap<String, Boolean>()


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
