package com.gwolf.nytbestsellers.util

sealed class AppError {
    data object Ignored : AppError()
    data object SessionNotExist : AppError()
    data object FailedRetrieveData : AppError()
    data object UnknownHostException : AppError()
    data object NoCredential : AppError()
    data class Unexpected(val cause: Throwable? = null) : AppError()
}