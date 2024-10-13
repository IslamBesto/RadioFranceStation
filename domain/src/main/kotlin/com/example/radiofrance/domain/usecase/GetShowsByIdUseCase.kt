package com.example.radiofrance.domain.usecase

import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.model.ShowDomain

class GetShowsByIdUseCase(private val brandRepository: IBrandRepository) {
    suspend operator fun invoke(id: String): List<ShowDomain> = brandRepository.getShowsById(id = id)
}