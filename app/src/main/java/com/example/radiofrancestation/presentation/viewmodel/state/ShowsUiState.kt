package com.example.radiofrancestation.presentation.viewmodel.state

sealed interface ShowsUiState {
    data object Loading : ShowsUiState
    data class Success(val shows: List<ShowUiModel>) : ShowsUiState
}

data class ShowUiModel(
    val title: String,
    val episodeCount: Int,
)