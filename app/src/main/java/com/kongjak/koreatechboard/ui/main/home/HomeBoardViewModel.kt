package com.kongjak.koreatechboard.ui.main.home

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.usecase.GetBoardMinimumUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBoardViewModel @Inject constructor(private val getBoardMinimumUseCase: GetBoardMinimumUseCase) :
    BaseViewModel<HomeBoardState, HomeBoardEvent>(HomeBoardState()) {

    fun getApi(site: String, board: String) {
        sendEvent(HomeBoardEvent.FetchData(site, board))
    }

    override fun reduce(oldState: HomeBoardState, event: HomeBoardEvent) {
        when (event) {
            is HomeBoardEvent.FetchData -> {
                setState(
                    oldState.copy(
                        isLoaded = false
                    )
                )
                viewModelScope.launch {
                    runCatching {
                        getBoardMinimumUseCase(event.department, event.board)
                    }.onSuccess {
                        when (it) {
                            is ResponseResult.Success -> {
                                setState(
                                    oldState.copy(
                                        isSuccess = true,
                                        boardData = it.data.boardData ?: emptyList(),
                                        statusCode = it.data.statusCode,
                                        isLoaded = true
                                    )
                                )
                            }

                            is ResponseResult.Error -> {
                                setState(
                                    oldState.copy(
                                        isSuccess = false,
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
