package com.example.radiofrancestation.data

import com.apollographql.apollo.ApolloClient
import com.example.GetBrandsQuery
import com.example.GetShowsByIdQuery
import com.example.radiofrancestation.domain.BrandClient
import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.radiofrancestation.domain.model.ShowDomain
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

    override suspend fun getShowsById(id: String): List<ShowDomain> {
        return apolloClient
            .query(GetShowsByIdQuery(StationsEnum.valueOf(id), first = 10))
            .execute()
            .data
            ?.shows
            ?.toShowDetailDomain() ?: emptyList()
    }
}