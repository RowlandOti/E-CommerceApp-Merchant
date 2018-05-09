package com.rowland.delivery.features.dash.domain.usecases.order;

import com.rowland.delivery.features.dash.data.model.order.OrderRepository;
import com.rowland.delivery.features.dash.domain.models.order.Order;
import com.rowland.delivery.features.dash.domain.models.order.OrderData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Rowland on 5/8/2018.
 */

public class LoadOrdersUseCase {

    private OrderRepository orderRepository;

    @Inject
    public LoadOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flowable<List<OrderData>> loadOrders(String userUID) {
        return this.orderRepository.getOrders(userUID);
    }
}
