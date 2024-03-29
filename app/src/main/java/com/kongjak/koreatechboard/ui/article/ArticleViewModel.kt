package com.kongjak.koreatechboard.ui.article

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.usecase.api.GetArticleUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : BaseViewModel<ArticleState, ArticleEvent>(ArticleState()) {

    fun getArticleData(department: String, uuid: UUID) {
        sendEvent(ArticleEvent.FetchData(department, uuid))
    }

    override fun reduce(oldState: ArticleState, event: ArticleEvent) {
        when (event) {
            is ArticleEvent.FetchData -> {
                viewModelScope.launch {
                    setState(oldState.copy(isLoading = true, isLoaded = false))

                    runCatching {
                        getArticleUseCase(event.uuid)
                    }.onSuccess {
                        when (it) {
                            is ResponseResult.Success -> {
                                setState(
                                    oldState.copy(
                                        isSuccess = true,
                                        article = it.data,
                                        isLoading = false,
                                        isLoaded = true,
                                        url = it.data.articleUrl
                                    )
                                )
                            }

                            is ResponseResult.Error -> {
                                setState(
                                    oldState.copy(
                                        isSuccess = false,
                                        isLoading = false,
                                        statusCode = it.errorType.statusCode,
                                        error = it.errorType.statusCode.toString()
                                    )
                                )
                            }
                        }
                    }.onFailure {
                        setState(
                            oldState.copy(
                                isSuccess = false,
                                isLoading = false,
                                error = it.localizedMessage ?: "",
                                isLoaded = true
                            )
                        )
                    }
                }
            }
        }
    }
}
