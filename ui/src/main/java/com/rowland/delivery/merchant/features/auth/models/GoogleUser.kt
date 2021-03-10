package com.rowland.delivery.merchant.features.auth.models

/**
 * Created by Rowland on 5/2/2018.
 */
class GoogleUser : User() {

    var displayName: String? = null
    var photoUrl: String? = null

    override fun toString(): String {
        return "GoogleUser(displayName=$displayName, photoUrl=$photoUrl)"
    }
}