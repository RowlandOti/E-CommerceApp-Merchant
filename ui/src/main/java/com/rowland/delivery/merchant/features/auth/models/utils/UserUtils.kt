/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant.features.auth.models.utils

import com.google.android.gms.auth.api.identity.SignInCredential
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

    fun populateGoogleUserFromSignInCredentials(result: SignInCredential): GoogleUser {
        val googleUser = GoogleUser()

        googleUser.displayName = result.displayName
        googleUser.photoUrl = result.profilePictureUri?.toString()

        googleUser.email = result.id
        googleUser.idToken = result.googleIdToken
        googleUser.userId = result.id
        googleUser.username = result.givenName

        return googleUser
    }
}