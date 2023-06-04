package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) :
    ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article

    private val _uuid = MutableLiveData<UUID>()
    val uuid: LiveData<UUID>
        get() = _uuid

    private val _site = MutableLiveData<String>()
    val site: LiveData<String>
        get() = _site

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setUUIDData(uuid: UUID) {
        _uuid.value = uuid
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    fun getArticleData() {
        viewModelScope.launch {
            _isLoading.value = true
            _article.value = getArticleUseCase.execute(site.value!!, uuid.value!!)
            _isLoading.value = false
        }
    }
}
