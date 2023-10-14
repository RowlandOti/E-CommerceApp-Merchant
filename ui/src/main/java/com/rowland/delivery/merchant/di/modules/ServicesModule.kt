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

package com.rowland.delivery.merchant.di.modules

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rowland.delivery.data.contracts.IPreferencesManager
import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.sharedpreferences.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 4/30/2018.
 */

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun providesSharedPreferencesManager(@ApplicationContext context: Context): IPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    fun providesSessionManager(preferencesManager: IPreferencesManager, firebaseAuth: FirebaseAuth): SessionManager {
        return SessionManager(preferencesManager, firebaseAuth)
    }
}
