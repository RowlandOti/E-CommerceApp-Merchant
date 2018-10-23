package com.rowland.delivery.presentation.viewmodels.product

sealed class ProductEvent {
    class Edit : ProductEvent()
    class Unpublish : ProductEvent()
}