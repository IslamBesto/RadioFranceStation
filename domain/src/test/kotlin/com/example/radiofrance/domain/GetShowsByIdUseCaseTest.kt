package com.example.radiofrance.domain

import com.example.radiofrance.domain.model.ShowDomain
import com.example.radiofrance.domain.usecase.GetShowsByIdUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetShowsByIdUseCaseTest : StringSpec({

    val brandRepo = mockk<IBrandRepository>()
    val getShowsByIdUseCase = GetShowsByIdUseCase(brandRepo)

    "should return list of shows when shows are found" {
        // Given
        val brandId = "123"
        val expectedShows = listOf(
            ShowDomain(title = "Show 1", episodeCount = 10),
            ShowDomain(title = "Show 2", episodeCount = 20)
        )

        // When
        coEvery { brandRepo.getShowsById(brandId) } returns expectedShows

        // Execute the use case
        val result = getShowsByIdUseCase(brandId)

        // Then
        result shouldBe expectedShows
    }

    "should throw an exception when shows are not found" {
        // Given
        val brandId = "invalid-id"

        // When
        coEvery { brandRepo.getShowsById(brandId) } throws Exception("Shows not found")

        // Then
        val exception = shouldThrow<Exception> {
            getShowsByIdUseCase(brandId)
        }

        exception.message shouldBe "Shows not found"
    }
})