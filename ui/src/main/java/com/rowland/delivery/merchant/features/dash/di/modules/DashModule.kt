package com.rowland.delivery.merchant.features.dash.di.modules


import com.rowland.delivery.merchant.di.modules.*
import com.rowland.delivery.merchant.features.dash.adapters.CategoryAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderDataAdapter
import com.rowland.delivery.merchant.features.dash.adapters.OrderItemAdapter
import com.rowland.delivery.merchant.features.dash.adapters.ProductAdapter
import com.rowland.delivery.merchant.services.firebase.modules.FirebaseModule
import com.rowland.delivery.merchant.services.session.di.modules.SessionModule
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = arrayOf(FirebaseModule::class, ContextModule::class, SessionModule::class, PresentationModule::class, DataModule::class,
        UiModule::class, DataModule::class, DomainModule::class, PresentationModule::class, RemoteModule::class, CacheModule::class,FirebaseModule::class))
class DashModule {

    @Provides
    //@ProductScope
    internal fun providesProductAdapter(): ProductAdapter {
        return ProductAdapter()
    }

    @Provides
    //@CategoryScope
    internal fun providesCategoryAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    @Provides
    //@OrderScope
    internal fun providesOrderDataAdapter(): OrderDataAdapter {
        return OrderDataAdapter()
    }

    @Provides
    //@OrderScope
    internal fun providesOrderItemAdapter(): OrderItemAdapter {
        return OrderItemAdapter()
    }
}
