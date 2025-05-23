package com.gwolf.nytbestsellers.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun signInWithGoogle(): Flow<DataResult<FirebaseUser?>>
}