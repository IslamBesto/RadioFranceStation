package com.example.radiofrance.domain

import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.model.ShowDomain

interface IBrandRepository {
    suspend fun getBrands(): List<BrandDomain>
    suspend fun getShowsById(id: String): List<ShowDomain>
}