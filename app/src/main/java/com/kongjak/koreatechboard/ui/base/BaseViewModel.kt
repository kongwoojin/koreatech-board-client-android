package com.kongjak.koreatechboard.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<S : UiState, E : UiEvent>(initialState: S) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState get() = _uiState.asStateFlow()

    fun sendEvent(event: E) {
        reduce(_uiState.value, event)
    }

    fun setState(newState: S) {
        _uiState.update { newState }
    }

    open fun reduce(oldState: S, event: E) {}
}
