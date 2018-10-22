package com.rowland.delivery.features.dash.di.modules

import com.rowland.delivery.features.dash.data.model.category.CategoryRepository
import com.rowland.delivery.features.dash.data.model.order.OrderRepository
import com.rowland.delivery.features.dash.data.model.product.ProductRepository
import com.rowland.delivery.features.dash.domain.contracts.ICategoryRepository
import com.rowland.delivery.features.dash.domain.contracts.IOrderRepository
import com.rowland.delivery.features.dash.domain.contracts.IProductRepository
import dagger.Binds
import dagger.Module

@Module()
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindProductRepository(productRepository: ProductRepository): IProductRepository

    @Binds
    internal abstract fun bindOrderRepository(orderRepository: OrderRepository): IOrderRepository

    @Binds
    internal abstract fun bindCategoryRepository(categoryRepository: CategoryRepository): ICategoryRepository
}