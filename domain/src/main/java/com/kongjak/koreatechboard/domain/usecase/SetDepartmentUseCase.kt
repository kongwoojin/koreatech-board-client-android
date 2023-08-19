package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SetDepartmentUseCase @Inject constructor(private val settingsRepository: SettingsRepository){
    suspend operator fun invoke(department: String) {
        settingsRepository.setDepartment(department)
    }
}