package com.rowland.delivery.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rowland.delivery.presentation.data.Resource

/**
 * Created by Rowland on 5/9/2018.
 */

abstract class SharedViewModel<T> : ViewModel() {

    protected val dataList = MutableLiveData<Resource<List<T>>>()
    protected val selectedListItem = MutableLiveData<T>()

    open fun setSelectedListItem(position: Int) {
        selectedListItem.value = dataList.value!!.data!![position]
    }

    fun getSelectedListItem(): LiveData<T> {
        return selectedListItem
    }

    fun getDataList(): LiveData<Resource<List<T>>> {
        return dataList
    }
}