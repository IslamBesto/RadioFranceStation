package com.example.radiofrance.domain.usecase

import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.model.BrandDomain

class GetBrandsUseCase(private val brandRepository: IBrandRepository) {
    suspend operator fun invoke(): List<BrandDomain> = brandRepository.getBrands().sortedBy { it.title }
}