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