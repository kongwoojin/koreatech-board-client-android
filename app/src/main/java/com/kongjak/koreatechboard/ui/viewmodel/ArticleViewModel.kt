package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) : ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    private val _site = MutableLiveData<String>()
    val site: LiveData<String>
        get() = _site

    private lateinit var job: Job

    fun setUrlData(url: String) {
        _url.value = url
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    fun getArticleData() {
        job = CoroutineScope(Dispatchers.IO).launch {
            _article.postValue(getArticleUseCase.execute(site.value!!, url.value!!))
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}