package com.example.radiofrancestation.domain.usecase

import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.model.BrandDetailDomain

class GetBrandByIdUseCase(private val brandClient: BrandClient) {
    suspend operator fun invoke(id: String): BrandDetailDomain = brandClient.getBrandById(id = id)
}