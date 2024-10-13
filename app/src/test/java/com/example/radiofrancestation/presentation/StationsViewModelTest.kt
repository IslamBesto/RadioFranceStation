package com.example.radiofrancestation.presentation

import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.radiofrancestation.domain.usecase.GetBrandsUseCase
import com.example.radiofrancestation.presentation.viewmodel.StationsViewModel
import com.example.radiofrancestation.presentation.viewmodel.state.StationsUiState
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
class StationsViewModelTest : BehaviorSpec({

    val testDispatcher = UnconfinedTestDispatcher()

    val getBrandsUseCase = mockk<GetBrandsUseCase>()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
    }


    afterTest {
        Dispatchers.resetMain()
    }

    given("a StationsViewModel") {

        `when`("the view model is created") {

            and("the use case is fetching brands") {

                then("the first emitted state should be Loading") {
                    runTest(testDispatcher) {
                        // Given
                        coEvery { getBrandsUseCase() } returns emptyList()

                        // When
                        val viewModel by lazy { StationsViewModel(getBrandsUseCase) }

                        // Then
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
                        // Given
                        val brands = listOf(
                            BrandDomain("1", "Brand A", "Description A"),
                            BrandDomain("2", "Brand B", "Description B")
                        )
                        coEvery { getBrandsUseCase() } returns brands

                        // When
                        val viewModel by lazy { StationsViewModel(getBrandsUseCase) }

                        // Then
                        val emittedStates = mutableListOf<StationsUiState>()
                        val job = launch {
                            viewModel.uiState.take(2).toList(emittedStates) // Prendre Loading et Success
                        }

                        advanceUntilIdle()

                        emittedStates[0] shouldBe StationsUiState.Loading // Premier état doit être Loading
                        emittedStates[1] shouldBe StationsUiState.Success(brands.map { it.toStationUiModel() }) // Deuxième état Success

                        job.cancel()
                    }
                }
            }
        }
    }
})
