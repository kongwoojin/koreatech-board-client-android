package com.kongjak.koreatechboard.domain.usecase.database

import com.kongjak.koreatechboard.domain.model.LocalArticle
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository

class InsertNewNoticeUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(localArticle: LocalArticle) {
        databaseRepository.insertArticle(localArticle)
    }
}
