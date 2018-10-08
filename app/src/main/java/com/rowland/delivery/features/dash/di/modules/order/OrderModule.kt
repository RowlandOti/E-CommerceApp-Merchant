package com.rowland.delivery.features.dash.di.modules.order

import com.rowland.delivery.features.dash.di.scope.order.OrderScope
import com.rowland.delivery.features.dash.presentation.adapters.OrderDataAdapter
import com.rowland.delivery.features.dash.presentation.adapters.OrderItemAdapter
import com.rowland.delivery.services.firebase.modules.FirebaseModule

import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 5/9/2018.
 */

@Module(includes = arrayOf(FirebaseModule::class))
class OrderModule {

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