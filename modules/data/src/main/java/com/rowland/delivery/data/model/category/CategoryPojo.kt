package com.rowland.delivery.data.model.category

import java.util.*

class CategoryPojo {

    var name: String? = null
    var merchants = HashMap<String, Boolean>()
    var firestoreUid: String? = null

    constructor()

    constructor(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "CategoryPojo(name=$name, merchants=$merchants, firestoreUid=$firestoreUid)"
    }


}