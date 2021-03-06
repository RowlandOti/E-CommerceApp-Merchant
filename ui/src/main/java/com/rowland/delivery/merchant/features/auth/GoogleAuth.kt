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

package com.rowland.delivery.merchant.features.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.merchant.features.auth.models.GoogleUser
import com.rowland.delivery.merchant.features.auth.models.Group
import com.rowland.delivery.merchant.features.auth.models.utils.UserUtils
import com.rowland.delivery.merchant.services.session.SessionManager
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirestore
import java.util.HashMap
import javax.inject.Inject

/**
 * Created by Rowland on 5/1/2018.
 */

class GoogleAuth @Inject
constructor(
    private val mAuthConfig: AuthConfig,
    private val mFirebaseAuth: FirebaseAuth,
    private val mFirebaseFirestone: FirebaseFirestore,
    private val mSessionManager: SessionManager
) : Auth() {

    override fun login() {
        mAuthConfig.getGoogleSignClient()?.let {
            mAuthConfig.activity.startActivityForResult(it.signInIntent, RC_SIGN_IN)
        }
    }

    override fun logout(context: Context): Boolean {
        try {
            mAuthConfig.getGoogleSignClient()?.let {
                it.signOut()
                    .addOnCompleteListener(mAuthConfig.activity) {
                        mSessionManager.logout()
                    }
            }

            return true
        } catch (e: Exception) {
            e.message?.let { Log.e("Logout Failed", it) }
            return false
        }
    }

    override fun register() {
    }

    override fun passwordReset() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            val acct = result?.signInAccount
            val googleUser = UserUtils.populateGoogleUser(acct!!)

            firebaseAuthWithGoogle(googleUser)
        }
    }

    private fun firebaseAuthWithGoogle(user: GoogleUser) {
        val credential = GoogleAuthProvider.getCredential(user.idToken, null)

        RxFirebaseAuth.signInWithCredential(mFirebaseAuth, credential).subscribe {
            val firebaseUser = mFirebaseAuth.currentUser
            user.userId = firebaseUser!!.uid
            configureUserAccount(user)
        }
    }

    private fun configureUserAccount(user: GoogleUser) {
        val batch = mFirebaseFirestone.batch()

        val usersCollectionRef = mFirebaseFirestone.collection("users")
        batch.set(usersCollectionRef.document(user.userId!!), user)

        val groupUsersCollectionRef = mFirebaseFirestone.collection("groups")
        val members = HashMap<String, Any>()
        members[String.format("members.%s", user.userId)] = true
        batch.update(groupUsersCollectionRef.document(Group.MERCHANT), members)

        RxFirestore.atomicOperation(batch).subscribe(
            {
                mSessionManager.setLogin(user.idToken!!)
                mAuthConfig.callback.onLoginSuccess()
                Toast.makeText(mAuthConfig.activity, "Account Setup Successful", Toast.LENGTH_SHORT).show()
            },
            { throwable ->
                mAuthConfig.callback.onLoginFailure(AuthException(throwable.message!!, throwable))
                Toast.makeText(mAuthConfig.activity, "Account Setup Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        )
    }

    companion object {

        private val RC_SIGN_IN = 7001
    }
}
