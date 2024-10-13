package com.example.radiofrancestation.presentation

import androidx.lifecycle.SavedStateHandle
import com.example.radiofrancestation.domain.model.ShowDomain
import com.example.radiofrancestation.domain.usecase.GetShowsByIdUseCase
import com.example.radiofrancestation.presentation.viewmodel.ShowsViewModel
import com.example.radiofrancestation.presentation.viewmodel.state.ShowsUiState
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

@OptIn(ExperimentalCoroutinesApi::class)
class ShowsViewModelTest : BehaviorSpec({

    val testDispatcher = UnconfinedTestDispatcher()
    val getShowsByIdUseCase = mockk<GetShowsByIdUseCase>()
    val savedStateHandle = SavedStateHandle(mapOf("stationId" to "testStationId"))

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    given("a ShowsViewModel") {

        `when`("the view model is created") {

            and("the use case is fetching shows") {

                then("the first emitted state should be Loading") {
                    runTest(testDispatcher) {
                        // Given
                        coEvery { getShowsByIdUseCase("testStationId") } returns emptyList()

                        // When
                        val viewModel by lazy { ShowsViewModel(getShowsByIdUseCase, savedStateHandle) }

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
                        // Given
                        val shows = listOf(
                            ShowDomain("1", 10),
                            ShowDomain("2", null)
                        )
                        coEvery { getShowsByIdUseCase("testStationId") } returns shows

                        // When
                        val viewModel by lazy { ShowsViewModel(getShowsByIdUseCase, savedStateHandle) }

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
})
