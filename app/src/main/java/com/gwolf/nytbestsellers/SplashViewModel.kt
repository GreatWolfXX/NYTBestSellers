package com.gwolf.nytbestsellers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwolf.nytbestsellers.domain.usecase.CheckSessionUseCase
import com.gwolf.nytbestsellers.navigation.Screen
import com.gwolf.nytbestsellers.util.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<Screen?>(null)
    val state: StateFlow<Screen?> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = null
    )

    init {
        viewModelScope.launch {
            checkSessionUseCase.invoke().collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        _state.update { Screen.Lists }
                    }

                    is DataResult.Error -> {
                        Timber.e("Session error: ${result.exception}")
                        _state.update { Screen.Auth }
                    }
                }
            }
        }
    }
}