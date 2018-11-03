package com.rowland.delivery.presentation.viewmodels.order

import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.usecases.order.LoadOrdersUseCase
import com.rowland.delivery.domain.usecases.order.UpdateOrderUseCase
import com.rowland.delivery.presentation.data.Resource
import com.rowland.delivery.presentation.data.ResourceState
import com.rowland.delivery.presentation.mapper.order.OrderDataMapper
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.viewmodels.SharedViewModel
import io.reactivex.Completable
import io.reactivex.subscribers.DisposableSubscriber
import java.util.*
import javax.inject.Inject

/**
 * Created by Rowland on 5/8/2018.
 */

class OrderViewModel @Inject constructor(private val loadOrdersUseCase: LoadOrdersUseCase, private val updateOrderUseCase: UpdateOrderUseCase, private val mapper: OrderDataMapper) : SharedViewModel<OrderDataModel>() {


    fun loadOrders(userUID: String) {
        this.loadOrdersUseCase.execute(OrderDataSubscriber(), LoadOrdersUseCase.Params.forOrders(userUID))
    }

    fun updateOrder(orderUpdateFields: HashMap<String, Any>): Completable {
        return updateOrderUseCase.updateOrder(orderUpdateFields, selectedListItem.value!!.firestoreUid!!)
    }

    inner class OrderDataSubscriber : DisposableSubscriber<List<OrderDataEntity>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<OrderDataEntity>) {
            dataList.postValue(Resource(ResourceState.SUCCESS, t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            dataList.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
