package com.example.radiofrancestation.domain

import com.example.radiofrancestation.domain.model.ShowDomain
import com.example.radiofrancestation.domain.model.BrandDomain

interface BrandClient {
    suspend fun getBrands(): List<BrandDomain>
    suspend fun getShowsById(id: String): List<ShowDomain>
}