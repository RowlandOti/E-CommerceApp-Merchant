package com.rowland.delivery.presentation.model.category

class CategoryModel {

    var name: String? = null
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