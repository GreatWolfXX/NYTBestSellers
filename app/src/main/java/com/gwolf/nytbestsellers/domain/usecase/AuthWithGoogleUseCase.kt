package com.gwolf.nytbestsellers.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.gwolf.nytbestsellers.domain.repository.FirebaseRepository
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class AuthWithGoogleUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(): Flow<DataResult<FirebaseUser?>> =
        firebaseRepository.signInWithGoogle().catch { exception ->
            emit(DataResult.Error(AppError.Unexpected(exception)))
        }
}