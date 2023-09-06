package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInitDepartmentUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<Int> {
        return settingsRepository.getInitDepartment()
    }
}
