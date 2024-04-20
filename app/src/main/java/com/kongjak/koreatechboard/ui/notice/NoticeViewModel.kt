package com.kongjak.koreatechboard.ui.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.database.DeleteArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.database.GetAllNewArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.database.UpdateArticleReadUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.ui.main.settings.deptList
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase
) : ContainerHost<NoticeState, NoticeSideEffect>, ViewModel() {
    override val container = container<NoticeState, NoticeSideEffect>(NoticeState())

    init {
        viewModelScope.launch {
            getUserDepartmentUseCase().collectLatest {
                intent {
                    reduce {
                        state.copy(userDepartment = it)
                    }
                }
            }
        }
    }

    fun getAllNotices() {
        intent {
            val noticesDeptList = listOf(
                Department.School,
                Department.Dorm,
                deptList[state.userDepartment]
            )
            postSideEffect(NoticeSideEffect.GetAllNotices(state.selectedDepartment.map { noticesDeptList[it].name }))
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
            is NoticeSideEffect.GetAllNotices -> viewModelScope.launch(Dispatchers.IO) {
                intent {
                    getAllNewArticleUseCase(*(sideEffect.departments.toTypedArray())).collect {
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

            is NoticeSideEffect.AddSelectedDepartment -> intent {
                reduce {
                    state.copy(selectedDepartment = state.selectedDepartment + sideEffect.department)
                }
            }

            is NoticeSideEffect.RemoveSelectedDepartment -> {
                intent {
                    reduce {
                        state.copy(selectedDepartment = state.selectedDepartment - sideEffect.department)
                    }
                }
            }
        }
    }

    fun addSelectedDepartment(department: Int) = intent {
        postSideEffect(NoticeSideEffect.AddSelectedDepartment(department))
    }

    fun removeSelectedDepartment(department: Int) = intent {
        postSideEffect(NoticeSideEffect.RemoveSelectedDepartment(department))
    }
}
