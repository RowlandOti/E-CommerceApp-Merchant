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

import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.merchant.executor.UiThread
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that provides all dependencies from the mobile package/layer and injects all activities.
 */
@Module()
@InstallIn(SingletonComponent::class)
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): IPostExecutionThread
}
