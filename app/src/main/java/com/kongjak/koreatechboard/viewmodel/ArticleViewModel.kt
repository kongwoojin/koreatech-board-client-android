package com.kongjak.koreatechboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(private val getArticleUseCase: GetArticleUseCase) : ViewModel() {
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
        CoroutineScope(Dispatchers.IO).launch {
            getArticleUseCase.execute(site.value!!, url.value!!)
                .collect { articleData ->
                    _article.postValue(articleData)
                }
        }
    }
}