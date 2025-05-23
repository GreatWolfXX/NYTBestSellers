package com.gwolf.nytbestsellers.util

sealed class AppError {
    data object SessionNotExist : AppError()
    data class Unexpected(val cause: Throwable? = null) : AppError()
}