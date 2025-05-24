package com.gwolf.nytbestsellers.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Flow<DataResult<FirebaseUser?>> = flow {
        try {
            val currentUser = auth.currentUser

            if (currentUser != null) {
                emit(DataResult.Success(currentUser))
            } else {
                emit(DataResult.Error(AppError.SessionNotExist))
            }
        } catch (e: Exception) {
            emit(
                DataResult.Error(AppError.Unexpected(e))
            )
        }
    }
}