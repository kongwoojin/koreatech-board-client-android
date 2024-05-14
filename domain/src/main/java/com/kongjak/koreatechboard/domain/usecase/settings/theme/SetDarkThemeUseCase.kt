package com.kongjak.koreatechboard.domain.usecase.settings.theme

import com.kongjak.koreatechboard.domain.repository.SettingsRepository

class SetDarkThemeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(theme: Int) {
        settingsRepository.setDarkTheme(theme)
    }
}
