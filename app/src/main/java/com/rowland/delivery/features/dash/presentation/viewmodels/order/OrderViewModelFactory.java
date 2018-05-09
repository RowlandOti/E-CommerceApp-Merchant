package com.rowland.delivery.features.dash.presentation.viewmodels.order;

import android.arch.lifecycle.ViewModelProvider;

import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;

import javax.inject.Inject;

/**
 * Created by Rowland on 5/9/2018.
 */

public class OrderViewModelFactory implements ViewModelProvider.Factory {

    private final LoadOrdersUseCase loadOrdersUseCase;

    @Inject
    public OrderViewModelFactory(LoadOrdersUseCase loadOrdersUseCase) {
        this.loadOrdersUseCase = loadOrdersUseCase;
    }

    @Override
    public OrderViewModel create(Class modelClass) {
        return new OrderViewModel(loadOrdersUseCase);
    }
}
