package com.gwolf.nytbestsellers.domain.usecase

import androidx.credentials.exceptions.GetCredentialCancellationException
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
            when (exception) {
                is GetCredentialCancellationException -> {
                    emit(DataResult.Error(AppError.Ignored))
                }

                else -> {
                    emit(DataResult.Error(AppError.Unexpected(exception)))
                }
            }
        }
}