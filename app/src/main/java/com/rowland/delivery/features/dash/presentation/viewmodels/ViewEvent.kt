package com.rowland.delivery.features.dash.presentation.viewmodels

import com.etiennelenhart.eiffel.state.ViewEvent

sealed class ViewEvent : ViewEvent() {
    class Loading : ViewEvent()
    class Success : ViewEvent()
    class Error : ViewEvent()
    class Empty : ViewEvent()
}