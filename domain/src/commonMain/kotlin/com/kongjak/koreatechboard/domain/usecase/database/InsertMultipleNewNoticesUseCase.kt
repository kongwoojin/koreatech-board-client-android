package com.kongjak.koreatechboard.domain.usecase.database

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository

class InsertMultipleNewNoticesUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(localArticleList: List<Uuid>, department: String, board: String) {
        databaseRepository.insertArticleList(localArticleList, department, board)
    }
}
