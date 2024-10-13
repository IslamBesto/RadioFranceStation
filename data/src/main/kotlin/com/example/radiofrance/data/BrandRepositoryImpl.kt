package com.example.radiofrance.data

import com.example.radiofrance.domain.IBrandRepository
import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.model.ShowDomain

class BrandRepositoryImpl(private val dataSource: RadioFranceDataSource) : IBrandRepository {
    override suspend fun getBrands(): List<BrandDomain> {
        return dataSource.getBrands()
    }

    override suspend fun getShowsById(id: String): List<ShowDomain> {
        return dataSource.getShowsById(id)
    }
}