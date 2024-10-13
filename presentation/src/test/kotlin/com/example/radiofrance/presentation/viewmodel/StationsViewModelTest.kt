package com.example.radiofrance.presentation.viewmodel

import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.usecase.GetBrandsUseCase
import com.example.radiofrance.presentation.viewmodel.state.StationsUiState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class StationsViewModelTest : BehaviorSpec(), KoinTest {

    init {
        val testDispatcher = UnconfinedTestDispatcher()

        beforeTest {
            stopKoin()
            startKoin {
                modules(
                    module {
                        single { mockk<IBrandRepository>(relaxUnitFun = true) }
                        factory { GetBrandsUseCase(get()) }
                    }
                )
            }
            Dispatchers.setMain(testDispatcher)
        }

        afterTest {
            Dispatchers.resetMain()
            stopKoin()
        }

        given("a StationsViewModel") {

            `when`("the view model is created") {

                and("the use case is fetching brands") {

                    then("the first emitted state should be Loading") {
                        runTest(testDispatcher) {
                            val brandRepository: IBrandRepository by inject()
                            val getBrandsUseCase: GetBrandsUseCase by inject()
                            coEvery { brandRepository.getBrands() } returns emptyList()

                            val viewModel by lazy { StationsViewModel(getBrandsUseCase) }

                            val emittedStates = mutableListOf<StationsUiState>()
                            val job = launch {
                                viewModel.uiState.take(1).toList(emittedStates)
                            }

                            advanceUntilIdle()

                            emittedStates[0] shouldBe StationsUiState.Loading

                            job.cancel()
                        }
                    }

                    then("the second emitted state should be Success with a list of stations") {
                        runTest(testDispatcher) {
                            val brands = listOf(
                                BrandDomain("1", "Brand A", "Description A"),
                                BrandDomain("2", "Brand B", "Description B")
                            )
                            val brandRepository: IBrandRepository by inject()
                            val getBrandsUseCase: GetBrandsUseCase by inject()
                            coEvery { brandRepository.getBrands() } returns brands

                            val viewModel by lazy { StationsViewModel(getBrandsUseCase) }

                            val emittedStates = mutableListOf<StationsUiState>()
                            val job = launch {
                                viewModel.uiState.take(2).toList(emittedStates)
                            }

                            advanceUntilIdle()

                            emittedStates[0] shouldBe StationsUiState.Loading
                            emittedStates[1] shouldBe StationsUiState.Success(brands.map { it.toStationUiModel() })

                            job.cancel()
                        }
                    }
                }
            }
        }
    }
}
