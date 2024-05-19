package com.kongjak.koreatechboard.ui.article

import androidx.lifecycle.viewModelScope
import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.usecase.api.GetArticleUseCase
import com.kongjak.koreatechboard.util.ViewModelExt
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class ArticleViewModel(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModelExt<ArticleState, ArticleSideEffect>(ArticleState()) {

    fun getArticleData(department: String, uuid: Uuid) {
        intent {
            postSideEffect(ArticleSideEffect.FetchData(department, uuid))
        }
    }

    fun handleSideEffect(sideEffect: ArticleSideEffect) {
        when (sideEffect) {
            is ArticleSideEffect.FetchData -> {
                viewModelScope.launch {
                    intent {
                        reduce {
                            state.copy(
                                isLoading = true,
                                isLoaded = false
                            )
                        }
                    }
                    runCatching {
                        getArticleUseCase(sideEffect.uuid)
                    }.onSuccess {
                        when (it) {
                            is APIResult.Success -> {
                                intent {
                                    reduce {
                                        state.copy(
                                            isSuccess = true,
                                            article = it.data,
                                            isLoading = false,
                                            isLoaded = true,
                                            url = it.data.articleUrl
                                        )
                                    }
                                }
                            }

                            is APIResult.Error -> {
                                intent {
                                    reduce {
                                        state.copy(
                                            isSuccess = false,
                                            isLoading = false,
                                            statusCode = it.errorType.statusCode,
                                            error = it.errorType.statusCode.toString()
                                        )
                                    }
                                }
                            }
                        }
                    }.onFailure {
                        intent {
                            reduce {
                                state.copy(
                                    isSuccess = false,
                                    isLoading = false,
                                    error = it.localizedMessage ?: "",
                                    isLoaded = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
