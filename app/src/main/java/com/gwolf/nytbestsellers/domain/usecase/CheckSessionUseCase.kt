package com.gwolf.nytbestsellers.domain.usecase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.util.DataResult
import com.gwolf.nytbestsellers.util.LocalizedText
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Flow<DataResult<FirebaseUser?>> = flow {
        try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                emit(DataResult.Success(currentUser))
            } else {
                emit(
                    DataResult.Error(
                        Exception(
                            LocalizedText.StringResource(R.string.err_session_not_exist)
                                .asString(context)
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataResult.Error(exception = e))
        }
    }
}