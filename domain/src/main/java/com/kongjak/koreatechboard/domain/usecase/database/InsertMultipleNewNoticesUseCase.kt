package com.kongjak.koreatechboard.domain.usecase.database

import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import java.util.*

class InsertMultipleNewNoticesUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(localArticleList: List<UUID>, department: String, board: String) {
        databaseRepository.insertArticleList(localArticleList, department, board)
    }
}
