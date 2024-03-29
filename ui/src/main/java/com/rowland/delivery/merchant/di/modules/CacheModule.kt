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

import com.rowland.delivery.cache.source.category.CategoryCacheSource
import com.rowland.delivery.cache.source.order.OrderCacheSource
import com.rowland.delivery.cache.source.product.ProductCacheSource
import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.contracts.product.IProductCacheSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    internal abstract fun bindProductCacheSource(productCache: ProductCacheSource): IProductCacheSource

    @Binds
    abstract fun bindCategoryCacheSource(categoryCache: CategoryCacheSource): ICategoryCacheSource

    @Binds
    abstract fun bindOrderCacheSource(orderCache: OrderCacheSource): IOrderCacheSource
}