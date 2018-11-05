package com.rowland.delivery.presentation.model.category

import java.util.*

class CategoryModel {

    var name: String? = null
    var merchants = HashMap<String, Boolean>()
    var firestoreUid: String? = null

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