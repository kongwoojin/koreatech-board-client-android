package com.kongjak.koreatechboard.domain.usecase.settings.theme

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetDynamicThemeUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> {
        return settingsRepository.getDynamicTheme()
    }
}
