package com.rowland.delivery.features.dash.presentation.viewmodels.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.rowland.delivery.features.dash.domain.models.order.Order;
import com.rowland.delivery.features.dash.domain.models.order.OrderData;
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase;
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rowland on 5/8/2018.
 */

public class OrderViewModel extends SharedViewModel<OrderData> {

    private final MutableLiveData<String> firebaseUserUid = new MutableLiveData<>();
    private final LoadOrdersUseCase loadOrdersUseCase;

    public OrderViewModel(LoadOrdersUseCase loadOrdersUseCase) {
        this.loadOrdersUseCase = loadOrdersUseCase;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ToDo: clean subscriptions
    }

    public LiveData<OrderData> getSelectedOrderData() {
        return selectedListItem;
    }

    public void setSelectedListItem(int selectedPosition) {
       selectedListItem.setValue(dataList.getValue().get(selectedPosition));
    }


    public void setFirebaseUserUid(String userUid) {
        firebaseUserUid.setValue(userUid);
    }

    @Override
    public LiveData<List<OrderData>> getDataList() {
        loadOrdersUseCase.loadOrders(firebaseUserUid.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordersData -> {
                    dataList.setValue(ordersData);
                }, throwable -> {
                    Log.d(OrderViewModel.class.getSimpleName(), "Error Retrieving List: " + throwable.getMessage());
                }, () -> {
                    Log.d(OrderViewModel.class.getSimpleName(), "Done Retrieving");
                });
        return dataList;
    }

    public Completable updateOrderStatus(String status) {
        return loadOrdersUseCase.updateOrderStatus(status, selectedListItem.getValue().getFirestoreUid());
    }
}
