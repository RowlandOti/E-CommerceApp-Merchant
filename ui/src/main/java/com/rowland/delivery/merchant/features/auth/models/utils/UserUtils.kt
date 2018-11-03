package com.rowland.delivery.merchant.features.auth.models.utils

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.rowland.delivery.merchant.features.auth.models.GoogleUser


/**
 * Created by Rowland on 5/2/2018.
 */

object UserUtils {
    fun populateGoogleUser(account: GoogleSignInAccount): GoogleUser {

        val googleUser = GoogleUser()

        googleUser.displayName = account.displayName
        if (account.photoUrl != null) {
            googleUser.photoUrl = account.photoUrl!!.toString()
        }
        googleUser.email = account.email
        googleUser.idToken = account.idToken
        googleUser.userId = account.id
        if (account.account != null) {
            googleUser.username = account.account!!.name
        }

        return googleUser
    }
}
