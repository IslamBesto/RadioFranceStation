package com.example.radiofrance.data

import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.model.ShowDomain
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class BrandRepositoryImplTest : BehaviorSpec({

    val dataSource = mockk<RadioFranceDataSource>()
    val repository = BrandRepositoryImpl(dataSource)

    given("a BrandRepositoryImpl") {

        `when`("getBrands is called and the data source returns an empty list") {
            coEvery { dataSource.getBrands() } returns emptyList()

            then("the result should be an empty list") {
                runTest {
                    val result = repository.getBrands()
                    result.shouldBeEmpty()
                }
            }
        }

        `when`("getBrands is called and the data source returns a list of brands") {
            val brands = listOf(
                BrandDomain("Brand1", "Title1", "BaseLine1"),
                BrandDomain("Brand2", "Title2", null)
            )
            coEvery { dataSource.getBrands() } returns brands

            then("the result should match the list from the data source") {
                runTest {
                    val result = repository.getBrands()
                    result shouldBe brands
                    result shouldHaveSize 2
                }
            }
        }

        `when`("getShowsById is called with a valid ID and the data source returns a list of shows") {
            val shows = listOf(
                ShowDomain("Show1", 10),
                ShowDomain("Show2", null)
            )
            coEvery { dataSource.getShowsById("testId") } returns shows

            then("the result should match the list from the data source") {
                runTest {
                    val result = repository.getShowsById("testId")
                    result shouldBe shows
                    result shouldHaveSize 2
                }
            }
        }

        `when`("getShowsById is called with a valid ID and the data source returns an empty list") {
            coEvery { dataSource.getShowsById("testId") } returns emptyList()

            then("the result should be an empty list") {
                runTest {
                    val result = repository.getShowsById("testId")
                    result.shouldBeEmpty()
                }
            }
        }
    }
})
