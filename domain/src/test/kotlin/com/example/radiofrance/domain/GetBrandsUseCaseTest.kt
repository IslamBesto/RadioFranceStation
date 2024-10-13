package com.example.radiofrance.domain

import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.usecase.GetBrandsUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetBrandsUseCaseTest : StringSpec({

    val mockBrandRepo = mockk<IBrandRepository>()
    val getBrandsUseCase = GetBrandsUseCase(mockBrandRepo)

    "invoke should return sorted list of brands" {
        // Arrange
        val unsortedBrands = listOf(
            BrandDomain("2", "Zebra", "Zebra is a brand"),
            BrandDomain("1", "Apple", "Apple is a brand"),
            BrandDomain("3", "Mango", "Mango is a brand")
        )

        coEvery { mockBrandRepo.getBrands() } returns unsortedBrands

        // Act
        val result = getBrandsUseCase()

        // Assert
        result shouldBe listOf(
            BrandDomain("1", "Apple", "Apple is a brand"),
            BrandDomain("3", "Mango", "Mango is a brand"),
            BrandDomain("2", "Zebra", "Zebra is a brand")
        )
    }

    "invoke should return an empty list if no brands are found" {
        // Arrange
        coEvery { mockBrandRepo.getBrands() } returns emptyList()

        // Act
        val result = getBrandsUseCase()

        // Assert
        result shouldBe emptyList()
    }
})
