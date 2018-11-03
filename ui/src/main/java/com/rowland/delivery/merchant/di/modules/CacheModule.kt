package com.rowland.delivery.merchant.di.modules

import com.rowland.delivery.cache.source.category.CategoryCacheSource
import com.rowland.delivery.cache.source.order.OrderCacheSource
import com.rowland.delivery.cache.source.product.ProductCacheSource
import com.rowland.delivery.data.contracts.category.ICategoryCacheSource
import com.rowland.delivery.data.contracts.order.IOrderCacheSource
import com.rowland.delivery.data.contracts.product.IProductCacheSource
import dagger.Binds
import dagger.Module


@Module()
abstract class CacheModule {

    @Binds
    internal abstract fun bindProductCacheSource(productCache: ProductCacheSource): IProductCacheSource

    @Binds
    abstract fun bindCategoryCacheSource(categoryCache: CategoryCacheSource): ICategoryCacheSource

    @Binds
    abstract fun bindOrderCacheSource(orderCache: OrderCacheSource): IOrderCacheSource
}