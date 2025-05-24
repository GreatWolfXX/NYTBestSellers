package com.gwolf.nytbestsellers.ui.screen.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.domain.entity.ResultEntity
import com.gwolf.nytbestsellers.domain.usecase.GetListsUseCase
import com.gwolf.nytbestsellers.domain.usecase.GetResultUseCase
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

data class ListsScreenState(
    val result: ResultEntity? = null,
    val listsList: List<ListEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
)

sealed class ListsIntent {
    data class ItemClick(val listId: Int, val listName: String) : ListsIntent()
    data object Refresh : ListsIntent()
}

sealed class ListsEvent {
    data object Idle : ListsEvent()
    data class Navigate(val listId: Int, val listName: String) : ListsEvent()
}

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getResultUseCase: GetResultUseCase,
    private val getListsUseCase: GetListsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(ListsScreenState())
    val state: StateFlow<ListsScreenState> = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ListsScreenState()
        )

    private var _event: Channel<ListsEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: ListsIntent) {
        when (intent) {
            is ListsIntent.ItemClick -> {
                viewModelScope.launch {
                    _event.send(ListsEvent.Navigate(intent.listId, intent.listName))
                }
            }

            ListsIntent.Refresh -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private suspend fun getData() {
        _state.update { it.copy(isLoading = true) }
        getResultUseCase.invoke().collect { response ->
            when (response) {
                is DataResult.Success -> {
                    val data = response.data
                    _state.update { it.copy(result = data) }
                    getLists(data.bestsellersDate)
                }

                is DataResult.Error -> {
                    val error = response.error
                    Timber.e("Get result error: $error")
                    if (error != AppError.Ignored) {
                        _state.update { it.copy(error = error) }
                    }
                }
            }
        }
        _state.update { it.copy(isLoading = false) }
    }

    private suspend fun getLists(resultBestsellersDate: String) {
        getListsUseCase.invoke(resultBestsellersDate).collect { response ->
            when (response) {
                is DataResult.Success -> {
                    _state.update { it.copy(listsList = response.data) }
                }

                is DataResult.Error -> {
                    val error = response.error
                    Timber.e("Get lists error: $error")
                    if (error != AppError.Ignored) {
                        _state.update { it.copy(error = error) }
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            getData()
        }
    }
}