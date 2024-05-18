package com.kongjak.koreatechboard.domain.usecase.database

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository

class DeleteNewNoticeUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(uuid: Uuid) {
        databaseRepository.deleteArticle(uuid)
    }
}
