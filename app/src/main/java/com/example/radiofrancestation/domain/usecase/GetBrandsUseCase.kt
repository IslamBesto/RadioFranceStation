package com.example.radiofrancestation.domain.usecase

import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.model.BrandDomain

class GetBrandsUseCase(private val brandClient: BrandClient) {
    suspend operator fun invoke(): List<BrandDomain> = brandClient.getBrands().sortedBy { it.title }
}