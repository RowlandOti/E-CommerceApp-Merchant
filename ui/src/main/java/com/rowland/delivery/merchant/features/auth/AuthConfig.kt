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
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient

/**
 * Created by Rowland on 5/1/2018.
 */

class AuthConfig(val activity: Activity, val callback: Auth.AuthLoginCallbacks) {

    private var googleSignInClient: GoogleSignInClient? = null

    private var beginSignInRequestBuilder: BeginSignInRequest.Builder? = null

    fun getGoogleSignClient(): GoogleSignInClient? {
        return this.googleSignInClient
    }

    fun setGoogleSignInClient(googleSignInClient: GoogleSignInClient): AuthConfig {
        this.googleSignInClient = googleSignInClient
        return this
    }

    fun getBeginSignInRequestBuilder(): BeginSignInRequest.Builder? {
        return this.beginSignInRequestBuilder
    }

    fun setBeginSignInRequestBuilder(beginSignInRequestBuilder: BeginSignInRequest.Builder): AuthConfig {
        this.beginSignInRequestBuilder = beginSignInRequestBuilder
        return this
    }
}
