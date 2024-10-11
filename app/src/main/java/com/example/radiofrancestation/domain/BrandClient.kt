package com.example.radiofrancestation.domain

import com.example.radiofrancestation.domain.model.BrandDetailDomain
import com.example.radiofrancestation.domain.model.BrandDomain

interface BrandClient {
    suspend fun getBrands(): List<BrandDomain>
    suspend fun getBrandById(id: String): BrandDetailDomain
}