package com.rowland.delivery.features.dash.di.components

import com.rowland.delivery.features.dash.di.components.category.CategoryComponent
import com.rowland.delivery.features.dash.di.components.order.OrderComponent
import com.rowland.delivery.features.dash.di.components.product.ProductComponent
import com.rowland.delivery.features.dash.di.modules.DashModule
import com.rowland.delivery.features.dash.di.modules.category.CategoryModule
import com.rowland.delivery.features.dash.di.modules.order.OrderModule
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rowland on 5/6/2018.
 */

@Component(modules = arrayOf(DashModule::class))
@Singleton
interface DashComponent {
    fun getCategoryComponent(categoryModule: CategoryModule): CategoryComponent

    fun getOrderComponent(orderModule: OrderModule): OrderComponent

    fun getProductComponent(productModule: ProductModule): ProductComponent

    fun inject(dashActivity: DashActivity)
}
