package com.rowland.delivery.merchant.di.modules

import com.rowland.delivery.domain.contracts.IOrderRepository
import com.rowland.delivery.domain.executor.IPostExecutionThread
import com.rowland.delivery.domain.executor.IThreadExecutor
import com.rowland.delivery.domain.usecases.order.LoadOrdersUseCase
import com.rowland.delivery.domain.usecases.order.UpdateOrderUseCase
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import com.rowland.delivery.presentation.mapper.order.OrderDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

/**
 * Module that provides all dependencies from the domain package/layer.
 */
@Module
@InstallIn(ActivityComponent::class)
object DomainModule {

/*    @Provides
    internal fun providesLoadOrdersUseCase(
        orderRepository: IOrderRepository,
        threadExecutor: IThreadExecutor,
        postExecutionThread: IPostExecutionThread
    ): LoadOrdersUseCase {
        return LoadOrdersUseCase(orderRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    internal fun providesUpdateOrderUseCase(
        orderRepository: IOrderRepository,
        threadExecutor: IThreadExecutor,
        postExecutionThread: IPostExecutionThread
    ): UpdateOrderUseCase {
        return UpdateOrderUseCase(orderRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    internal fun providesOrderDataMapper(
    ): OrderDataMapper {
        return OrderDataMapper()
    }*/
}