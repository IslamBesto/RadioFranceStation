package com.example.radiofrancestation.domain.usecase

import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.model.ShowDomain

class GetShowsByIdUseCase(private val brandClient: BrandClient) {
    suspend operator fun invoke(id: String): List<ShowDomain> = brandClient.getShowsById(id = id)
}