package com.kongjak.koreatechboard.domain.usecase.database

import com.kongjak.koreatechboard.domain.model.LocalArticle
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNewArticleUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(): Flow<List<LocalArticle>> {
        return databaseRepository.getArticleList()
    }
}
