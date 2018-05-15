package com.rowland.delivery.features.dash.di.components;

import com.rowland.delivery.features.dash.di.components.category.CategoryComponent;
import com.rowland.delivery.features.dash.di.components.order.OrderComponent;
import com.rowland.delivery.features.dash.di.components.product.ProductComponent;
import com.rowland.delivery.features.dash.di.modules.DashModule;
import com.rowland.delivery.features.dash.di.modules.category.CategoryModule;
import com.rowland.delivery.features.dash.di.modules.order.OrderModule;
import com.rowland.delivery.features.dash.di.modules.product.ProductModule;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rowland on 5/6/2018.
 */

@Component(modules = {DashModule.class})
@Singleton
public interface DashComponent {
    CategoryComponent getCategoryComponent(CategoryModule categoryModule);

    OrderComponent getOrderComponent(OrderModule orderModule);

    ProductComponent getProductComponent(ProductModule productModule);

    void inject(DashActivity dashActivity);
}
