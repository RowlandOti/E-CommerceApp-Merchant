package com.rowland.delivery.presentation.mapper.order


import com.rowland.delivery.domain.models.order.OrderDataEntity
import com.rowland.delivery.domain.models.order.OrderItemEntity
import com.rowland.delivery.presentation.mapper.IMapper
import com.rowland.delivery.presentation.model.order.OrderDataModel
import com.rowland.delivery.presentation.model.order.OrderItemModel
import javax.inject.Inject

open class OrderDataMapper @Inject constructor() : IMapper<OrderDataModel, OrderDataEntity> {

    companion object {

        fun mapOrderItemToView(type: OrderItemEntity): OrderItemModel {
            val orderItem = OrderItemModel()
            orderItem.itemCode = type.itemCode
            orderItem.title = type.title
            orderItem.imageUrl = type.imageUrl
            orderItem.itemQuantity = type.itemQuantity
            orderItem.price = type.price
            orderItem.itemTotal = type.itemTotal
            orderItem.foreign = type.foreign

            return orderItem
        }
    }

    override fun mapToView(type: OrderDataEntity): OrderDataModel {
        val product = OrderDataModel()
        product.id = type.id
        product.orderRef = type.orderRef
        product.status = type.status
        product.createdAt = type.createdAt
        product.updatedAt = type.updatedAt
        product.itemIds = type.itemIds
        product.phone = type.phone
        product.name = type.name
        product.address = type.address
        product.lng = type.lng
        product.lat = type.lat
        product.orderDescription = type.orderDescription

        val orderItems = mutableListOf<OrderItemModel>()
        type.items!!.map { orderItems.add(mapOrderItemToView(it)) }
        product.items = orderItems

        product.deliveryFee = type.deliveryFee
        product.orderItemsQuantity = type.orderItemsQuantity
        product.orderSubTotal = type.orderSubTotal
        product.orderTotal = type.orderTotal
        product.firestoreUid = type.firestoreUid

        return product
    }
}