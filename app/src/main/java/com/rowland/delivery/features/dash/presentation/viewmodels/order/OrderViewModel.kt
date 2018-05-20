package com.rowland.delivery.features.dash.presentation.viewmodels.order

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rowland.delivery.features.dash.domain.models.order.OrderData
import com.rowland.delivery.features.dash.domain.usecases.order.LoadOrdersUseCase
import com.rowland.delivery.features.dash.presentation.viewmodels.SharedViewModel
import com.rowland.delivery.features.dash.presentation.viewmodels.ViewEvent
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderViewModel @Inject constructor(private val loadOrdersUseCase: LoadOrdersUseCase) : SharedViewModel<OrderData>() {

    init {
        loadOrdersUseCase.loadOrders(FirebaseAuth.getInstance().currentUser!!.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ordersData ->
                    dataList.value = ordersData
                    updateState { it.copy(event = ViewEvent.Success()) }
                }, { throwable ->
                    updateState { it.copy(event = ViewEvent.Error()) }
                }) {
                    Log.d(OrderViewModel::class.java.simpleName, "Done Retrieving")
                }
    }


    fun updateOrderStatus(status: String): Completable {
        return loadOrdersUseCase.updateOrderStatus(status, selectedListItem.value!!.firestoreUid)
    }
}
