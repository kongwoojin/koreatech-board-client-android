package com.kongjak.koreatechboard.domain.usecase.settings.theme

import com.kongjak.koreatechboard.domain.repository.SettingsRepository

class SetDynamicThemeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(state: Boolean) {
        settingsRepository.setDynamicTheme(state)
    }
}
