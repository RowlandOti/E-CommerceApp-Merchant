package com.rowland.delivery.features.dash.di.components.order;

import com.rowland.delivery.features.dash.di.modules.order.OrderModule;
import com.rowland.delivery.features.dash.di.scope.order.OrderScope;
import com.rowland.delivery.features.dash.presentation.fragments.OrderFragment;
import com.rowland.delivery.features.dash.presentation.fragments.OrderItemFragment;

import dagger.Subcomponent;

/**
 * Created by Rowland on 5/9/2018.
 */


@OrderScope
@Subcomponent(modules = OrderModule.class)
public interface OrderComponent {
    void inject(OrderFragment orderFragment);
    void inject(OrderItemFragment orderItemFragment);
}

