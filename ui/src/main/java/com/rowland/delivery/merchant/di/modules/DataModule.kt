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