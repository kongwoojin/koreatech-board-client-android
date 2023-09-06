package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SetInitDepartmentUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(index: Int) {
        settingsRepository.setInitDepartment(index)
    }
}
