package com.rowland.delivery.features.dash.di.modules;

import android.arch.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.data.model.order.OrderRepository;
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModelFactory;
import com.rowland.delivery.services.firebase.modules.FirebaseModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rowland on 5/6/2018.
 */

@Module(includes = FirebaseModule.class)
public class DashModule {

    @Provides
    @Named("order")
    ViewModelProvider.Factory providesViewModelFactory(LoadOrdersUseCase loadOrdersUseCase) {
        return new OrderViewModelFactory(loadOrdersUseCase);
    }

    @Provides
    LoadOrdersUseCase providesLoadOrdersUseCase(OrderRepository OrderRepository) {
        return new LoadOrdersUseCase(OrderRepository);
    }

    @Provides
    OrderRepository providesOrderRepository(FirebaseFirestore firebaseFirestone) {
        return new OrderRepository(firebaseFirestone);
    }
}
