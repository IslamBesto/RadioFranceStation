package com.example.radiofrance.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.model.ShowDomain
import com.example.radiofrance.domain.usecase.GetShowsByIdUseCase
import com.example.radiofrance.presentation.viewmodel.state.ShowsUiState
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
class ShowsViewModelTest : BehaviorSpec(), KoinTest {
    init {
        val testDispatcher = UnconfinedTestDispatcher()

        beforeTest {
            stopKoin()
            startKoin {
                modules(
                    module {
                        single { mockk<IBrandRepository>(relaxUnitFun = true) }
                        factory { GetShowsByIdUseCase(get()) }
                    }
                )
            }
            Dispatchers.setMain(testDispatcher)
        }

        afterTest {
            Dispatchers.resetMain()
            stopKoin()
        }

        given("a ShowsViewModel") {

            `when`("the view model is created") {

                and("the use case is fetching shows") {

                    then("the first emitted state should be Loading") {
                        runTest(testDispatcher) {
                            val brandRepository: IBrandRepository by inject()
                            coEvery { brandRepository.getShowsById("testStationId") } returns emptyList()

                            val getShowsByIdUseCase: GetShowsByIdUseCase by inject()
                            val savedStateHandle =
                                SavedStateHandle(mapOf("stationId" to "testStationId"))

                            // When
                            val viewModel by lazy {
                                ShowsViewModel(
                                    getShowsByIdUseCase,
                                    savedStateHandle
                                )
                            }

                            // Then
                            val emittedStates = mutableListOf<ShowsUiState>()
                            val job = launch {
                                viewModel.uiState.take(1).toList(emittedStates)
                            }

                            advanceUntilIdle()

                            emittedStates[0] shouldBe ShowsUiState.Loading

                            job.cancel()
                        }
                    }

                    then("the second emitted state should be Success with a list of shows") {
                        runTest(testDispatcher) {
                            val brandRepository: IBrandRepository by inject()
                            val shows = listOf(
                                ShowDomain("1", 10),
                                ShowDomain("2", null)
                            )
                            coEvery { brandRepository.getShowsById("testStationId") } returns shows

                            val getShowsByIdUseCase: GetShowsByIdUseCase by inject()
                            val savedStateHandle =
                                SavedStateHandle(mapOf("stationId" to "testStationId"))

                            // When
                            val viewModel by lazy {
                                ShowsViewModel(
                                    getShowsByIdUseCase,
                                    savedStateHandle
                                )
                            }

                            // Then
                            val emittedStates = mutableListOf<ShowsUiState>()
                            val job = launch {
                                viewModel.uiState.take(2).toList(emittedStates)
                            }

                            advanceUntilIdle()

                            emittedStates[0] shouldBe ShowsUiState.Loading
                            emittedStates[1] shouldBe ShowsUiState.Success(shows.map { it.toShowUiModel() })

                            job.cancel()
                        }
                    }
                }
            }
        }
    }
}

