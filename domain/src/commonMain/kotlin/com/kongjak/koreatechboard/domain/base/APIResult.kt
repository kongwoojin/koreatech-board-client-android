package com.kongjak.koreatechboard.domain.base

sealed class APIResult<T> {
    data class Success<T>(val data: T) : APIResult<T>()
    data class Error<T>(val errorType: ErrorType) : APIResult<T>()
}
