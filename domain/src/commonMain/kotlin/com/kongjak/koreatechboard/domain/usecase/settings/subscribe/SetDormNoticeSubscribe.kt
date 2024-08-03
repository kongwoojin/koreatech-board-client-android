package com.kongjak.koreatechboard.domain.usecase.settings.subscribe

import com.kongjak.koreatechboard.domain.repository.SettingsRepository

class SetDormNoticeSubscribe(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(subscribe: Boolean) =
        settingsRepository.setDormNoticeSubscribe(subscribe)
}
