package com.rowland.delivery.features.dash.presentation.viewmodels

import com.etiennelenhart.eiffel.state.ViewEvent
import com.etiennelenhart.eiffel.state.ViewState

data class ViewState(val event: ViewEvent = ViewEvent.None) : ViewState
