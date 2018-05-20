package com.rowland.delivery.features.dash.presentation.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by Rowland on 5/9/2018.
 */

abstract class SharedViewModel<T> : StateViewModel() {

    protected val dataList = MutableLiveData<List<T>>()
    protected val selectedListItem = MutableLiveData<T>()

    open fun setSelectedListItem(position: Int) {
        selectedListItem.value = dataList.value!![position]
    }

    fun getSelectedListItem(): LiveData<T> {
        return selectedListItem
    }

    fun getDataList(): LiveData<List<T>> {
        return dataList
    }
}