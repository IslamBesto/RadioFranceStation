package com.example.radiofrancestation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiofrancestation.domain.usecase.GetBrandsUseCase
import com.example.radiofrancestation.presentation.toStationUiModel
import com.example.radiofrancestation.presentation.viewmodel.state.StationsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StationsViewModel(private val getBrandsUseCase: GetBrandsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<StationsUiState>(StationsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getBrandsUseCase().let { brands ->
                _uiState.update { StationsUiState.Success(brands.map { it.toStationUiModel() }) }
            }
        }
    }
}