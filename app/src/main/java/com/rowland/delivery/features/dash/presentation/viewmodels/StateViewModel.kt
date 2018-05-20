package com.rowland.delivery.features.dash.presentation.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.etiennelenhart.eiffel.viewmodel.StateViewModel

abstract class StateViewModel : StateViewModel<ViewState>() {
    override val state = MutableLiveData<ViewState>()

    init {
        initState(ViewState())
        stateInitialized // true
    }

}

