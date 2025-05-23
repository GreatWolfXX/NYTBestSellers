package com.gwolf.nytbestsellers.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthWithGoogleUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(idToken: String): Flow<DataResult<FirebaseUser?>> = flow {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try {
            auth.signInWithCredential(credential).await()

            val user = auth.currentUser
            emit(DataResult.Success(user))
        } catch (e: Exception) {
            emit(DataResult.Error(AppError.Unexpected(e)))
        }
    }
}