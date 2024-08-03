package com.kongjak.koreatechboard.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

abstract class ViewModelExt<S : Any, SE : Any>(initialState: S) : ContainerHost<S, SE>, ViewModel() {
    override val container = viewModelScope.container<S, SE>(initialState)

    fun collectSideEffect(block: suspend (SE) -> Unit) = viewModelScope.launch {
        container.sideEffectFlow.collect { block(it) }
    }

    @Composable
    fun collectAsState() = container.stateFlow.collectAsState()
}
