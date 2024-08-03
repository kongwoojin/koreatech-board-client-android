package com.kongjak.koreatechboard.domain.usecase.settings.department

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetUserDepartmentUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<Int> {
        return settingsRepository.getUserDepartment()
    }
}
