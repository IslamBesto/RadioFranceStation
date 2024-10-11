package com.example.radiofrancestation.data

import com.apollographql.apollo.ApolloClient
import com.example.GetBrandByIdQuery
import com.example.GetBrandsQuery
import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.model.BrandDetailDomain
import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.type.StationsEnum

class ApolloBrandClient(
    private val apolloClient: ApolloClient
) : BrandClient {
    override suspend fun getBrands(): List<BrandDomain> {
        return apolloClient
            .query(GetBrandsQuery())
            .execute()
            .data
            ?.brands
            ?.mapNotNull { it?.toBrandDomain() } ?: emptyList()
    }

    override suspend fun getBrandById(id: String): BrandDetailDomain {
        return apolloClient
            .query(GetBrandByIdQuery(StationsEnum.valueOf(id)))
            .execute()
            .data
            ?.brand
            ?.toBrandDetailDomain() ?: throw IllegalStateException("Brand not found")
    }
}