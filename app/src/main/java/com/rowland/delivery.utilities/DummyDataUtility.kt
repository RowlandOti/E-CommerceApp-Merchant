package com.rowland.delivery.utilities

import com.rowland.delivery.features.dash.domain.models.order.OrderData
import com.rowland.delivery.features.dash.domain.models.order.OrderItem
import com.rowland.delivery.features.dash.domain.models.product.Product
import java.util.*

/**
 * Created by Rowland on 5/9/2018.
 */

object DummyDataUtility {

    fun createProduct(): Product {
        val product = Product()
        product.createdAt = Date()
        product.deletedAt = Date()
        product.updatedAt = Date()
        product.description = "Brown cake simmered in mayoinase"
        product.id = Random().nextInt(100)
        product.imageUrl = "https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg"
        product.itemCode = "tWd"
        product.itemQuantity = Random().nextInt(25)
        product.merchantCode = "34dsdfEd"
        product.name = "Brown Cake"
        product.saleQuantity = 3
        product.unitsOfMeasure = 4
        product.price = Random().nextInt(2000)

        return product
    }

    fun createOrderData(): OrderData {

        val data = OrderData()
        data.address = "River Side Drive 44"
        data.createdAt = Date().toString()
        data.lat = "45.0"
        data.lng = "34.5"
        data.id = 23
        data.itemIds = "TeUbdhD"
        data.name = randomCustomerName()
        data.deliveryFee = 250
        data.orderItemsQuantity = Random().nextInt(40)
        data.orderRef = "Tydsgeb"
        data.orderSubTotal = "34"
        data.orderTotal = 245
        data.updatedAt = Date().toString()
        data.status = randomStatus()
        data.orderDescription = "Hot and Sliced Hamper"
        data.phone = "0712847884"

        val items = ArrayList<OrderItem>()
        val item = OrderItem()
        item.foreign = false
        item.imageUrl = "https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg"
        item.itemCode = "rTs"
        item.itemQuantity = 15
        item.itemTotal = 40
        item.price = 1200
        item.title = "McFries Delicacies"

        items.add(item)

        val item2 = OrderItem()
        item2.foreign = false
        item2.imageUrl = "https://image.tmdb.org/t/p/w500/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg"
        item2.itemCode = "rWd"
        item2.itemQuantity = 3
        item2.itemTotal = 5
        item2.price = 200
        item2.title = "Brown Cake"

        items.add(item2)

        data.items = items

        return data
    }

    fun randomStatus(): String {
        val status = arrayOf("pending", "completed", "in_progress", "cancelled", "active", "failed", "delivered", "missed")
        return status[Random().nextInt(status.size - 1)]
    }

    fun randomCustomerName(): String {
        val names = arrayOf("Rowland", "Pete", "Antony", "Mary", "Cleo", "Faith", "Ron", "Tim")
        return names[Random().nextInt(names.size - 1)]
    }
}
