package com.example.radiofrance.presentation

import com.example.radiofrance.presentation.viewmodel.ShowsViewModel
import com.example.radiofrance.presentation.viewmodel.StationsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { StationsViewModel(get()) }
    viewModel { ShowsViewModel(get(), get()) }
}