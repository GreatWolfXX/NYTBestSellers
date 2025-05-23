package com.gwolf.nytbestsellers.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwolf.nytbestsellers.domain.usecase.AuthWithGoogleUseCase
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class AuthScreenState(
    val isLoading: Boolean = false,
    val error: AppError? = null,
)

sealed class AuthIntent {
    data object Auth : AuthIntent()
}

sealed class AuthEvent {
    data object Idle : AuthEvent()
    data object Navigate : AuthEvent()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authWithGoogleUseCase: AuthWithGoogleUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = AuthScreenState()
    )

    private var _event: Channel<AuthEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Auth -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authWithGoogleUseCase.invoke().collect { response ->
                when (response) {
                    is DataResult.Success -> {
                        _event.send(AuthEvent.Navigate)
                    }

                    is DataResult.Error -> {
                        val error = response.error
                        Timber.e("Auth error: $error")
                        if (error != AppError.Ignored) {
                            _state.update { it.copy(error = error) }
                        }
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}