package com.gwolf.nytbestsellers.ui.screen.books

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.domain.usecase.GetBooksUseCase
import com.gwolf.nytbestsellers.navigation.Screen
import com.gwolf.nytbestsellers.util.AppError
import com.gwolf.nytbestsellers.util.DataResult
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
import timber.log.Timber
import javax.inject.Inject

data class BooksScreenState(
    val listName: String = "",
    val booksList: List<BookEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
)

sealed class BooksIntent {
    data class ItemClick(val uri: String) : BooksIntent()
    data object Refresh : BooksIntent()
}

sealed class BooksEvent {
    data object Idle : BooksEvent()
    data class OpenLink(val uri: String) : BooksEvent()
}

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _state = MutableStateFlow(BooksScreenState())
    val state: StateFlow<BooksScreenState> = _state
        .onStart {
            getData(savedStateHandle)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = BooksScreenState()
        )

    private var _event: Channel<BooksEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: BooksIntent) {
        when (intent) {
            is BooksIntent.ItemClick -> {
                viewModelScope.launch {
                    _event.send(BooksEvent.OpenLink(intent.uri))
                }
            }

            BooksIntent.Refresh -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private suspend fun getData(savedStateHandle: SavedStateHandle) {
        val parameters = savedStateHandle.toRoute<Screen.Books>()
        _state.update { it.copy(listName = parameters.listName) }

        _state.update { it.copy(isLoading = true) }
        getBooksUseCase.invoke(parameters.listId).collect { response ->
            when (response) {
                is DataResult.Success -> {
                    val data = response.data
                    _state.update { it.copy(booksList = data) }
                }

                is DataResult.Error -> {
                    val error = response.error
                    Timber.e("Get books error: $error")
                    if (error != AppError.Ignored) {
                        _state.update { it.copy(error = error) }
                    }
                }
            }
        }
        _state.update { it.copy(isLoading = false) }
    }
}