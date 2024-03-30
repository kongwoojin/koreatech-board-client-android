package com.kongjak.koreatechboard.ui.main.home

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.usecase.api.GetBoardMinimumUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class HomeBoardViewModel @Inject constructor(private val getBoardMinimumUseCase: GetBoardMinimumUseCase) :
    BaseViewModel<HomeBoardState, HomeBoardEvent>(HomeBoardState()) {

    private val isFetching = AtomicBoolean(false)

    suspend fun getApi(department: String, board: String) = viewModelScope.launch {
        if (board in uiState.value.boardData && uiState.value.boardData[board]?.boardData!!.isNotEmpty()) return@launch
        while (isFetching.get()) {
            delay(1000)
        }
        sendEvent(HomeBoardEvent.FetchData(department, board))
    }

    override fun reduce(oldState: HomeBoardState, event: HomeBoardEvent) {
        when (event) {
            is HomeBoardEvent.FetchData -> {
                viewModelScope.launch {
                    isFetching.set(true)
                    setState(
                        oldState.copy(
                            boardData = oldState.boardData + (event.board to (oldState.boardData[event.board]?.copy(
                                isLoaded = false
                            ) ?: HomeBoardState.HomeBoardData(
                                isLoaded = false
                            )))
                        )
                    )

                    runCatching {
                        getBoardMinimumUseCase(event.department, event.board)
                    }.onSuccess {
                        when (it) {
                            is ResponseResult.Success -> {
                                setState(
                                    oldState.copy(
                                        boardData = oldState.boardData + (event.board to (oldState.boardData[event.board]?.copy(
                                            isSuccess = true,
                                            boardData = it.data.boardData ?: emptyList(),
                                            statusCode = it.data.statusCode,
                                            isLoaded = true
                                        ) ?: HomeBoardState.HomeBoardData(
                                            isSuccess = true,
                                            boardData = it.data.boardData ?: emptyList(),
                                            statusCode = it.data.statusCode,
                                            isLoaded = true
                                        )))
                                    )
                                )
                            }

                            is ResponseResult.Error -> {
                                setState(
                                    oldState.copy(
                                        boardData = oldState.boardData + (event.board to (oldState.boardData[event.board]?.copy(
                                            isSuccess = false,
                                            statusCode = it.errorType.statusCode,
                                            error = it.errorType.statusCode.toString()
                                        ) ?: HomeBoardState.HomeBoardData(
                                            isSuccess = false,
                                            statusCode = it.errorType.statusCode,
                                            error = it.errorType.statusCode.toString()
                                        )))
                                    )
                                )
                            }
                        }
                    }.onFailure {
                        setState(
                            oldState.copy(
                                boardData = oldState.boardData + (event.board to (oldState.boardData[event.board]?.copy(
                                    isSuccess = false,
                                    error = it.localizedMessage ?: "",
                                    isLoaded = true
                                ) ?: HomeBoardState.HomeBoardData(
                                    isSuccess = false,
                                    error = it.localizedMessage ?: "",
                                    isLoaded = true
                                )))
                            )
                        )
                    }
                    isFetching.set(false)
                }
            }
        }
    }
}
