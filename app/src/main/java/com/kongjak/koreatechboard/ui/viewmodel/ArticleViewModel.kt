package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) :
    ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    private val _site = MutableLiveData<String>()
    val site: LiveData<String>
        get() = _site

    fun setUrlData(url: String) {
        _url.value = url
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    fun getArticleData() {
        viewModelScope.launch {
            _article.value = getArticleUseCase.execute(site.value!!, url.value!!)
        }
    }
}
