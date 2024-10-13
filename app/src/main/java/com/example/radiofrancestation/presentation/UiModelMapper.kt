package com.example.radiofrancestation.presentation

import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.radiofrancestation.domain.model.ShowDomain
import com.example.radiofrancestation.presentation.viewmodel.state.ShowUiModel
import com.example.radiofrancestation.presentation.viewmodel.state.StationUiModel

fun BrandDomain.toStationUiModel(): StationUiModel = StationUiModel(
    id = id,
    title = title,
    baseLine = baseLine
)

fun ShowDomain.toShowUiModel(): ShowUiModel = ShowUiModel(
    title = title,
    episodeCount = episodeCount ?: 0
)