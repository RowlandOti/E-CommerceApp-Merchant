package com.rowland.delivery.merchant.features.dash.di.modules


import com.rowland.delivery.features.dash.di.scope.category.CategoryScope
import com.rowland.delivery.features.dash.di.scope.product.ProductScope
import com.rowland.delivery.merchant.di.modules.ContextModule
import com.rowland.delivery.merchant.di.modules.DataModule
import com.rowland.delivery.merchant.di.modules.PresentationModule
import com.rowland.delivery.merchant.di.scope.order.OrderScope
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = arrayOf(FirebaseModule::class, ContextModule::class, SessionModule::class, PresentationModule::class, DataModule::class))
class DashModule {

    @Provides
    @ProductScope
    internal fun providesProductAdapter(): ProductAdapter {
        return ProductAdapter()
    }

    @Provides
    @CategoryScope
    internal fun providesCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    @OrderScope
    internal fun providesOrderDataAdapter(): OrderDataAdapter {
        return OrderDataAdapter()
    }

    @Provides
    @OrderScope
    internal fun providesOrderItemAdapter(): OrderItemAdapter {
        return OrderItemAdapter()
    }
}
