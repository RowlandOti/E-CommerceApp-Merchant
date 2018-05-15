package com.rowland.delivery.features.dash.presentation.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by Rowland on 5/9/2018.
 */

public abstract class SharedViewModel<T> extends ViewModel {

    protected final MutableLiveData<List<T>> dataList = new MutableLiveData<>();
    protected final MutableLiveData<T> selectedListItem = new MutableLiveData<>();

    public void setSelectedListItem(int position) {
        selectedListItem.setValue(dataList.getValue().get(position));
    }

    public LiveData<T> getSelectedListItem() {
        return selectedListItem;
    }

    public abstract LiveData<List<T>> getDataList();
}