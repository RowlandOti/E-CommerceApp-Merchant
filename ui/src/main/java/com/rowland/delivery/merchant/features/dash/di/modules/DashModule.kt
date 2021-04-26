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

package com.rowland.delivery.merchant.features.dash.di.modules

import com.rowland.delivery.merchant.di.modules.CacheModule
import com.rowland.delivery.merchant.di.modules.DataModule
import com.rowland.delivery.merchant.di.modules.DomainModule
import com.rowland.delivery.merchant.di.modules.PresentationModule
import com.rowland.delivery.merchant.di.modules.RemoteModule
import com.rowland.delivery.merchant.di.modules.ServicesModule
import com.rowland.delivery.merchant.di.modules.UiModule
import com.rowland.delivery.merchant.features.dash.adapters.CategoryAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderDataAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderItemAdapter
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(
    includes = [
        ServicesModule::class, PresentationModule::class,
        DataModule::class, UiModule::class, DataModule::class, DomainModule::class, PresentationModule::class,
        RemoteModule::class, CacheModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class DashModule {

    @Provides
    internal fun providesProductAdapter(): ProductAdapter {
        return ProductAdapter()
    }

    @Provides
    internal fun providesCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    internal fun providesOrderDataAdapter(): OrderDataAdapter {
        return OrderDataAdapter()
    }

    @Provides
    internal fun providesOrderItemAdapter(): OrderItemAdapter {
        return OrderItemAdapter()
    }
}
