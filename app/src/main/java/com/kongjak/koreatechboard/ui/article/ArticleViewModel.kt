package com.kongjak.koreatechboard.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val getDynamicThemeUseCase: GetDynamicThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase
) :
    ViewModel() {
    private val _isDynamicTheme = MutableLiveData(true)
    val isDynamicTheme: LiveData<Boolean>
        get() = _isDynamicTheme

    private val _isDarkTheme = MutableLiveData<Boolean?>()
    val isDarkTheme: LiveData<Boolean?>
        get() = _isDarkTheme

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

    private val _statusCode = MutableLiveData(200)
    val statusCode: LiveData<Int>
        get() = _statusCode

    private val _url = MutableLiveData<String?>()
    val url: LiveData<String?>
        get() = _url

    init {
        getDynamicTheme()
        getDarkTheme()
    }

    fun setUUIDData(uuid: UUID) {
        _uuid.value = uuid
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    fun getArticleData() {
        viewModelScope.launch {
            _isLoading.value = true

            runCatching {
                getArticleUseCase.execute(site.value!!, uuid.value!!)
            }.onSuccess {
                when (it) {
                    is ResponseResult.Success -> {
                        _article.value = it.data!!
                        _statusCode.value = it.data.statusCode
                        _url.value = it.data.articleUrl
                    }

                    is ResponseResult.Error -> {
                        _statusCode.value = it.errorType.statusCode
                    }
                }
            }.onFailure {
            }

            _isLoading.value = false
        }
    }

    private fun getDynamicTheme() {
        viewModelScope.launch {
            getDynamicThemeUseCase().collectLatest {
                _isDynamicTheme.value = it
            }
        }
    }

    private fun getDarkTheme() {
        viewModelScope.launch {
            getDarkThemeUseCase().collectLatest {
                when (it) {
                    DARK_THEME_SYSTEM_DEFAULT -> {
                        _isDarkTheme.value = null
                    }

                    DARK_THEME_DARK_THEME -> {
                        _isDarkTheme.value = true
                    }

                    DARK_THEME_LIGHT_THEME -> {
                        _isDarkTheme.value = false
                    }
                }
            }
        }
    }
}
