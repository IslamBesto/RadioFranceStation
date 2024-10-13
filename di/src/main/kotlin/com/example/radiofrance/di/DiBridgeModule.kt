package com.example.radiofrance.di

import com.apollographql.apollo.ApolloClient
import com.example.radiofrance.data.BrandRepositoryImpl
import com.example.radiofrance.data.RadioFranceDataSource
import com.example.radiofrance.data.RadioFranceRemoteDataSourceImpl
import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.usecase.GetBrandsUseCase
import com.example.radiofrance.domain.usecase.GetShowsByIdUseCase
import org.koin.dsl.module

val diBridgeModule = module {
    single<RadioFranceDataSource> { RadioFranceRemoteDataSourceImpl(get()) }
    single<IBrandRepository> { BrandRepositoryImpl(get()) }

    single {
        ApolloClient.Builder()
            .serverUrl(
                "https://openapi.radiofrance.fr/v1/graphql?x-\n" +
                        "token=84c107b0-22f0-4958-883d-381edaa54174"
            ).build()
    }
    factory { GetShowsByIdUseCase(get()) }
    factory { GetBrandsUseCase(get()) }
}