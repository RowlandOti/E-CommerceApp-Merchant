package com.rowland.delivery.features.dash.di.modules.order;

import android.arch.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.data.model.order.OrderRepository;
import com.rowland.delivery.features.dash.di.modules.DashModule;
import com.rowland.delivery.features.dash.di.scope.order.Order;
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;
import com.rowland.delivery.features.dash.presentation.adapters.OrderDataAdapter;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModelFactory;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/9/2018.
 */

@Module(includes = {FirebaseModule.class, DashModule.class})
public class OrderModule {

    @Provides
    @Order
    OrderDataAdapter providesOrderDataAdapter() {
        return new OrderDataAdapter();
    }

/*    @Provides
    @Order
    @Named("order")
    ViewModelProvider.Factory providesViewModelFactory(LoadOrdersUseCase loadOrdersUseCase) {
        return new OrderViewModelFactory(loadOrdersUseCase);
    }

    @Provides
    @Order
    LoadOrdersUseCase providesLoadOrdersUseCase(OrderRepository OrderRepository) {
        return new LoadOrdersUseCase(OrderRepository);
    }

    @Provides
    @Order
    OrderRepository providesOrderRepository(FirebaseFirestore firebaseFirestone) {
        return new OrderRepository(firebaseFirestone);
        }*/
}
