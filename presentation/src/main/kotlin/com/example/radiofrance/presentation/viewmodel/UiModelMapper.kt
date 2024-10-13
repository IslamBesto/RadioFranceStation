package com.example.radiofrance.presentation.viewmodel

import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.model.ShowDomain
import com.example.radiofrance.presentation.viewmodel.state.ShowUiModel
import com.example.radiofrance.presentation.viewmodel.state.StationUiModel

fun BrandDomain.toStationUiModel(): StationUiModel = StationUiModel(
    id = id,
    title = title,
    baseLine = baseLine
)

fun ShowDomain.toShowUiModel(): ShowUiModel = ShowUiModel(
    title = title,
    episodeCount = episodeCount ?: 0
)