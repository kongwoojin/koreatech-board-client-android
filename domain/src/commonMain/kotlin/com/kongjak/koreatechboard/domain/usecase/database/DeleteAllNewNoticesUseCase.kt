package com.kongjak.koreatechboard.domain.usecase.database

import com.kongjak.koreatechboard.domain.repository.DatabaseRepository

class DeleteAllNewNoticesUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke() {
        databaseRepository.deleteAllArticle()
    }
}
