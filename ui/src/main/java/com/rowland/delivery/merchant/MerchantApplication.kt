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

package com.rowland.delivery.merchant

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.sharedcore.diagnostics.AppCenterManager
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

/**
 * Created by Rowland on 4/30/2018.
 */

@HiltAndroidApp
class MerchantApplication : MultiDexApplication() {

    @Inject
    internal lateinit var appCenterManager: AppCenterManager

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e -> Log.d(MerchantApplication::class.java.simpleName, e.toString()) }
        FirebaseFirestore.setLoggingEnabled(BuildConfig.DEBUG)
        appCenterManager.initialize(this)
    }
}
