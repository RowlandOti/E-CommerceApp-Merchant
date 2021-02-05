package com.rowland.delivery.merchant.features.dash.di.modules

import com.rowland.delivery.merchant.di.modules.CacheModule
import com.rowland.delivery.merchant.di.modules.DataModule
import com.rowland.delivery.merchant.di.modules.DomainModule
import com.rowland.delivery.merchant.di.modules.PresentationModule
import com.rowland.delivery.merchant.di.modules.RemoteModule
import com.rowland.delivery.merchant.di.modules.UiModule
import com.rowland.delivery.merchant.features.dash.adapters.CategoryAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderDataAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderItemAdapter
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import com.rowland.delivery.merchant.services.firebase.modules.FirebaseModule
import com.rowland.delivery.merchant.services.session.di.modules.SessionModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(
    includes = [FirebaseModule::class, SessionModule::class, PresentationModule::class,
        DataModule::class, UiModule::class, DataModule::class, DomainModule::class, PresentationModule::class,
        RemoteModule::class, CacheModule::class, FirebaseModule::class]
)
@InstallIn(SingletonComponent::class)
class DashModule {

    @Provides
    internal fun providesProductAdapter(): ProductAdapter {
        return ProductAdapter()
    }

    @Provides
    internal fun providesCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    internal fun providesOrderDataAdapter(): OrderDataAdapter {
        return OrderDataAdapter()
    }

    @Provides
    internal fun providesOrderItemAdapter(): OrderItemAdapter {
        return OrderItemAdapter()
    }
}
