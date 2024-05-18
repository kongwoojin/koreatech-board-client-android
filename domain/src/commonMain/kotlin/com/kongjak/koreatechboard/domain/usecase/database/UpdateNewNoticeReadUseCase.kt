package com.kongjak.koreatechboard.domain.usecase.database

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository

class UpdateNewNoticeReadUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(uuid: Uuid, read: Boolean) {
        databaseRepository.updateRead(uuid, read)
    }
}
