package com.gwolf.nytbestsellers.ui.screen.webview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gwolf.nytbestsellers.navigation.Screen
import com.gwolf.nytbestsellers.util.AppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WebViewScreenState(
    val uri: String = "",
    val error: AppError? = null,
)

sealed class WebViewIntent {
    data object NavigateBack : WebViewIntent()
    data object Refresh : WebViewIntent()
}

sealed class WebViewEvent {
    data object Idle : WebViewEvent()
    data object NavigateBack : WebViewEvent()
}

@HiltViewModel
class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _state = MutableStateFlow(WebViewScreenState())
    val state: StateFlow<WebViewScreenState> = _state
        .onStart {
            getData(savedStateHandle)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = WebViewScreenState()
        )

    private var _event: Channel<WebViewEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: WebViewIntent) {
        when (intent) {
            WebViewIntent.NavigateBack -> {
                viewModelScope.launch {
                    _event.send(WebViewEvent.NavigateBack)
                }
            }

            WebViewIntent.Refresh -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun getData(savedStateHandle: SavedStateHandle) {
        val parameters = savedStateHandle.toRoute<Screen.WebView>()
        _state.update { it.copy(uri = parameters.uri) }
    }
}