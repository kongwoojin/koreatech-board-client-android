package com.kongjak.koreatechboard.domain.usecase.database

import com.kongjak.koreatechboard.domain.model.LocalArticle
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAllNewArticleUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(): List<LocalArticle> {
        return databaseRepository.getArticleList()
    }
}
