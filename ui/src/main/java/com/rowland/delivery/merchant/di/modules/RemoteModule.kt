/*
 * Copyright 2021 Otieno Rowland
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

import com.rowland.delivery.data.contracts.category.ICategoryRemoteSource
import com.rowland.delivery.data.contracts.image.IImageRemoteSource
import com.rowland.delivery.data.contracts.order.IOrderRemoteSource
import com.rowland.delivery.data.contracts.product.IProductRemoteSource
import com.rowland.delivery.remote.source.category.CategoryRemoteSource
import com.rowland.delivery.remote.source.image.ImageRemoteSource
import com.rowland.delivery.remote.source.order.OrderRemoteSource
import com.rowland.delivery.remote.source.product.ProductRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {
    @Binds
    abstract fun bindProductRemote(productRemote: ProductRemoteSource): IProductRemoteSource

    @Binds
    abstract fun bindCategoryRemote(categoryRemote: CategoryRemoteSource): ICategoryRemoteSource

    @Binds
    abstract fun bindOrderRemote(orderRemote: OrderRemoteSource): IOrderRemoteSource

    @Binds
    abstract fun bindImageRemote(imageRemote: ImageRemoteSource): IImageRemoteSource
}
