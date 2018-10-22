package com.rowland.delivery.features.dash.di.modules.product

import com.rowland.delivery.features.dash.di.scope.product.ProductScope
import com.rowland.delivery.features.dash.presentation.adapters.ProductAdapter
import com.rowland.delivery.services.firebase.modules.FirebaseModule

import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 5/14/2018.
 */


@Module(includes = arrayOf(FirebaseModule::class))
class ProductModule {

    @Provides
    @ProductScope
    internal fun providesProductAdapter(): ProductAdapter {
        return ProductAdapter()
    }

}
