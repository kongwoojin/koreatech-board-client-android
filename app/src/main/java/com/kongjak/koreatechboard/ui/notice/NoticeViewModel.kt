package com.kongjak.koreatechboard.ui.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.database.DeleteArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.database.GetAllNewArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.database.UpdateArticleReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getAllNewArticleUseCase: GetAllNewArticleUseCase,
    private val updateArticleReadUseCase: UpdateArticleReadUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ContainerHost<NoticeState, NoticeSideEffect>, ViewModel() {
    override val container = container<NoticeState, NoticeSideEffect>(NoticeState())

    fun getAllNotices() {
        intent {
            postSideEffect(NoticeSideEffect.GetAllNotices)
        }
    }

    fun updateRead(uuid: UUID, read: Boolean) {
        intent {
            postSideEffect(NoticeSideEffect.UpdateRead(uuid, read))
        }
    }

    fun deleteNotice(uuid: UUID) {
        intent {
            postSideEffect(NoticeSideEffect.DeleteNotice(uuid))
        }
    }

    fun handleSideEffect(sideEffect: NoticeSideEffect) {
        when (sideEffect) {
            NoticeSideEffect.GetAllNotices -> viewModelScope.launch(Dispatchers.IO) {
                getAllNewArticleUseCase().collect {
                    intent {
                        reduce {
                            state.copy(articles = it, isLoaded = true)
                        }
                    }
                }
            }

            is NoticeSideEffect.UpdateRead -> viewModelScope.launch(Dispatchers.IO) {
                updateArticleReadUseCase(sideEffect.uuid, sideEffect.read)
            }

            is NoticeSideEffect.DeleteNotice -> viewModelScope.launch(Dispatchers.IO) {
                deleteArticleUseCase(sideEffect.uuid)
            }
        }
    }
}
