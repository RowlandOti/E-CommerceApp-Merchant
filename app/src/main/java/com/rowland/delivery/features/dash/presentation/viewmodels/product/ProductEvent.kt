package com.rowland.delivery.features.dash.presentation.viewmodels.product

sealed class ProductEvent {
    class Edit : ProductEvent()
    class Unpublish : ProductEvent()
}