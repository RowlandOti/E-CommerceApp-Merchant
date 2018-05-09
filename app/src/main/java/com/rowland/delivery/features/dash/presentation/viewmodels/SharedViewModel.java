package com.rowland.delivery.features.dash.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rowland.delivery.features.dash.domain.models.order.OrderData;

import java.util.List;

/**
 * Created by Rowland on 5/9/2018.
 */

public abstract class SharedViewModel<T> extends ViewModel {

    private final MutableLiveData<List<T>> dataList = new MutableLiveData<>();

    private final MutableLiveData<T> selected = new MutableLiveData<>();
    private OnSelectListener<T> listener;

    public interface OnSelectListener<T> {
        void selected(T item);
    }


    public void setListener(OnSelectListener<T> listener) {
        this.listener = listener;
    }

    public void setSelected(T item) {
        selected.setValue(item);
        listener.selected(item);
    }

    public LiveData<T> getSelected() {
        return selected;
    }

    public abstract MutableLiveData<List<OrderData>> getDataList();
}