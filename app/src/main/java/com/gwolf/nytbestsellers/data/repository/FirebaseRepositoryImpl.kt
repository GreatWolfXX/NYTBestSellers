package com.gwolf.nytbestsellers.data.repository

import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.gwolf.nytbestsellers.BuildConfig
import com.gwolf.nytbestsellers.domain.repository.FirebaseRepository
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth
) : FirebaseRepository {

    override fun signInWithGoogle(): Flow<DataResult<FirebaseUser?>> = flow {
        val credential = getGoogleCredential()

        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
            val user = auth.currentUser
            emit(DataResult.Success(user))
        } else {
            emit(DataResult.Error(AppError.Unexpected()))
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential).await()
    }

    private suspend fun getGoogleCredential(): Credential {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.FIREBASE_AUTH_CLIENT_ID)
            .setFilterByAuthorizedAccounts(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return credentialManager.getCredential(
            context = context,
            request = request
        ).credential
    }
}