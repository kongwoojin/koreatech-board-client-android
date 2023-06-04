package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _urlToOpenInBrowser = MutableLiveData<String>()
    val urlToOpenInBrowser: LiveData<String>
        get() = _urlToOpenInBrowser

    private val _isMenuNeeded = MutableLiveData<Boolean>()
    val isMenuNeeded: LiveData<Boolean>
        get() = _isMenuNeeded

    init {
        _isMenuNeeded.value = false
    }

    fun updateUrl(url: String) {
        _urlToOpenInBrowser.value = url
    }

    fun updateMenuNeeded(isMenuNeeded: Boolean) {
        _isMenuNeeded.value = isMenuNeeded
    }
}
