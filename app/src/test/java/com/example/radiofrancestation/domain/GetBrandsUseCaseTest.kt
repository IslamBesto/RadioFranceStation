package com.example.radiofrancestation.domain

import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.radiofrancestation.domain.usecase.GetBrandsUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetBrandsUseCaseTest : StringSpec({

    val mockBrandClient = mockk<BrandClient>()
    val getBrandsUseCase = GetBrandsUseCase(mockBrandClient)

    "invoke should return sorted list of brands" {
        // Arrange
        val unsortedBrands = listOf(
            BrandDomain("2", "Zebra", "Zebra is a brand"),
            BrandDomain("1", "Apple", "Apple is a brand"),
            BrandDomain("3", "Mango", "Mango is a brand")
        )

        coEvery { mockBrandClient.getBrands() } returns unsortedBrands

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
        coEvery { mockBrandClient.getBrands() } returns emptyList()

        // Act
        val result = getBrandsUseCase()

        // Assert
        result shouldBe emptyList()
    }
})
