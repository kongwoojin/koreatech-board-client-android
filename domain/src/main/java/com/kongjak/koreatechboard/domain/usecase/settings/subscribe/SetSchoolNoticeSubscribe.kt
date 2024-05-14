package com.kongjak.koreatechboard.domain.usecase.settings.subscribe

import com.kongjak.koreatechboard.domain.repository.SettingsRepository

class SetSchoolNoticeSubscribe(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(subscribe: Boolean) =
        settingsRepository.setSchoolNoticeSubscribe(subscribe)
}
