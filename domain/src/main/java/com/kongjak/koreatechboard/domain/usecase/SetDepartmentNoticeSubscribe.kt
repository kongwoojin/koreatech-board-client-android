package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SetDepartmentNoticeSubscribe @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(subscribe: Boolean) =
        settingsRepository.setDepartmentNoticeSubscribe(subscribe)
}
