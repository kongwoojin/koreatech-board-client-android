package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SetUserDepartmentUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(index: Int) {
        settingsRepository.setUserDepartment(index)
    }
}
