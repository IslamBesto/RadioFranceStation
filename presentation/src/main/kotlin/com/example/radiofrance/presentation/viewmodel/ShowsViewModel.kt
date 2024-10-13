package com.example.radiofrance.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiofrance.domain.usecase.GetShowsByIdUseCase
import com.example.radiofrance.presentation.PARAM_STATION_ID
import com.example.radiofrance.presentation.viewmodel.state.ShowsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowsViewModel(
    private val getShowsByIdUseCase: GetShowsByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val stationId = savedStateHandle.get<String>(PARAM_STATION_ID).orEmpty()
    private val _uiState = MutableStateFlow<ShowsUiState>(ShowsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getShowsByIdUseCase(stationId).let { shows ->
                _uiState.update { ShowsUiState.Success(shows.map { it.toShowUiModel() }) }
            }
        }
    }
}