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

import com.rowland.delivery.data.executor.JobExecutor
import com.rowland.delivery.data.repository.category.CategoryRepository
import com.rowland.delivery.data.repository.image.ImageRepository
import com.rowland.delivery.data.repository.order.OrderRepository
import com.rowland.delivery.data.repository.product.ProductRepository
import com.rowland.delivery.domain.contracts.ICategoryRepository
import com.rowland.delivery.domain.contracts.IImageRepository
import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.contracts.IProductRepository
import com.rowland.delivery.domain.executor.IThreadExecutor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindProductRepository(productRepository: ProductRepository): IProductRepository

    @Binds
    internal abstract fun bindOrderRepository(orderRepository: OrderRepository): IOrderRepository

    @Binds
    internal abstract fun bindCategoryRepository(categoryRepository: CategoryRepository): ICategoryRepository

    @Binds
    internal abstract fun bindImageRepository(imageRepository: ImageRepository): IImageRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): IThreadExecutor
}
