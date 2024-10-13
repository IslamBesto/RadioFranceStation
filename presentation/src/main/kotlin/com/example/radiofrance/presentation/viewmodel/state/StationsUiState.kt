package com.example.radiofrance.presentation.viewmodel.state

sealed interface StationsUiState {
    data object Loading : StationsUiState
    data class Success(val stations: List<StationUiModel>) : StationsUiState
}

data class StationUiModel(
    val id: String,
    val title: String,
    val baseLine: String?
)