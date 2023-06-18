package com.kongjak.koreatechboard.domain.base

sealed class ResponseResult<T> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error<T>(val errorType: ErrorType) : ResponseResult<T>()
}