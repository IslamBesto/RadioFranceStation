package com.example.radiofrancestation

import com.apollographql.apollo.ApolloClient
import com.example.radiofrancestation.data.ApolloBrandClient
import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.usecase.GetShowsByIdUseCase
import com.example.radiofrancestation.domain.usecase.GetBrandsUseCase
import com.example.radiofrancestation.presentation.viewmodel.ShowsViewModel
import com.example.radiofrancestation.presentation.viewmodel.StationsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<BrandClient> {
        ApolloBrandClient(
            ApolloClient.Builder()
                .serverUrl(
                    "https://openapi.radiofrance.fr/v1/graphql?x-\n" +
                            "token=84c107b0-22f0-4958-883d-381edaa54174"
                ).build()
        )
    }

    factoryOf(::GetShowsByIdUseCase)
    factoryOf(::GetBrandsUseCase)

    viewModel { StationsViewModel(get()) }
    viewModel { ShowsViewModel(get(), get()) }
}