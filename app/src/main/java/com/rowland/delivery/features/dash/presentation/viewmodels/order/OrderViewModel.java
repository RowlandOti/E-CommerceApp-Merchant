package com.rowland.delivery.features.dash.presentation.viewmodels.order;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/8/2018.
 */

public class OrderViewModel extends ViewModel {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();
    private final MutableLiveData<List<OrderData>> orderDataList = new MutableLiveData<>();
    private final MutableLiveData<OrderData> selectedOrderData = new MutableLiveData<>();

    private final LoadOrdersUseCase loadOrdersUseCase;

    public OrderViewModel(LoadOrdersUseCase loadOrdersUseCase) {
        this.loadOrdersUseCase = loadOrdersUseCase;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ToDo: clean subscriptions
    }

    public MutableLiveData<OrderData> getSelectedOrderData() {
        return selectedOrderData;
    }

    public void select(int selectedPosition) {
        selectedOrderData.setValue(orderDataList.getValue().get(selectedPosition));
    }

    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    public MutableLiveData<String> getFirebaseUserUid() {
        return firebaseUserUid;
    }

    public MutableLiveData<List<OrderData>> getOrderDataList() {
        loadOrdersUseCase.loadOrders(firebaseUserUid.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordersData -> {
                    orderDataList.setValue(ordersData);
                }, throwable -> {
                    Log.d(OrderViewModel.class.getSimpleName(), "Error Retrieving List: " + throwable.getMessage());
                }, () -> {
                    Log.d(OrderViewModel.class.getSimpleName(), "Done Retrieving");
                });
        return orderDataList;
    }

    public Completable updateOrderStatus(String status) {
        return loadOrdersUseCase.updateOrderStatus(status, selectedOrderData.getValue().getFirestoreUid());
    }
}
