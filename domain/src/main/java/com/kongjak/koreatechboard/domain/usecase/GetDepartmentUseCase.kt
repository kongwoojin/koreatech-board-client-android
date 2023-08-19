package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class GetDepartmentUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): String {
        return settingsRepository.getDepartment()
    }
}