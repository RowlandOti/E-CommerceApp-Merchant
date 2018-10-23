package com.rowland.delivery.merchant.features.dash.di.components

import com.rowland.delivery.features.dash.presentation.fragments.*
import com.rowland.delivery.merchant.features.dash.activities.DashActivity
import com.rowland.delivery.merchant.features.dash.di.modules.DashModule
import com.rowland.delivery.merchant.features.dash.fragments.*
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Rowland on 5/6/2018.
 */

@Component(modules = arrayOf(DashModule::class))
@Singleton
interface DashComponent {
    // Activities
    fun inject(dashActivity: DashActivity)
    // Fragments
    fun inject(productFragment: ProductFragment)
    fun inject(newProductFragment: NewProductFragment)
    fun inject(editProductFragment: EditProductFragment)
    fun inject(categoryFragment: CategoryFragment)
    fun inject(orderFragment: OrderFragment)
    fun inject(orderItemFragment: OrderItemFragment)
}
