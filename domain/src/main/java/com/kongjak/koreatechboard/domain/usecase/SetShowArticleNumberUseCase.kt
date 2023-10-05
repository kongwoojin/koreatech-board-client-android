package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SetShowArticleNumberUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(state: Boolean) {
        settingsRepository.setShowArticleNumber(state)
    }
}
