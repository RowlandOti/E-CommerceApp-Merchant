package com.rowland.delivery.features.dash.di.components.product

import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.di.scope.product.ProductScope
import com.rowland.delivery.features.dash.presentation.fragments.NewProductFragment
import com.rowland.delivery.features.dash.presentation.fragments.ProductFragment

import dagger.Subcomponent

/**
 * Created by Rowland on 5/14/2018.
 */

@ProductScope
@Subcomponent(modules = arrayOf(ProductModule::class))
interface ProductComponent {
    fun inject(productFragment: ProductFragment)
    fun inject(newProductFragment: NewProductFragment)
}