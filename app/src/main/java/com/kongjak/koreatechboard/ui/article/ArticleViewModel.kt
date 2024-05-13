package com.kongjak.koreatechboard.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.usecase.api.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ContainerHost<ArticleState, ArticleSideEffect>, ViewModel() {

    override val container = container<ArticleState, ArticleSideEffect>(ArticleState())

    fun getArticleData(department: String, uuid: UUID) {
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
