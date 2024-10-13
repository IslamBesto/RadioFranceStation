package com.example.radiofrance.data

import com.apollographql.apollo.ApolloClient
import com.example.GetBrandsQuery
import com.example.GetShowsByIdQuery
import com.example.radiofrance.domain.model.BrandDomain
import com.example.radiofrance.domain.model.ShowDomain
import com.example.type.StationsEnum

interface RadioFranceDataSource {
    suspend fun getBrands(): List<BrandDomain>
    suspend fun getShowsById(id: String): List<ShowDomain>
}

class RadioFranceRemoteDataSourceImpl(
    private val apolloClient: ApolloClient
) : RadioFranceDataSource {
    override suspend fun getBrands(): List<BrandDomain> {
        return apolloClient
            .query(GetBrandsQuery())
            .execute()
            .data
            ?.brands
            ?.mapNotNull { it?.toBrandDomain() } ?: emptyList()
    }

    override suspend fun getShowsById(id: String): List<ShowDomain> {
        return try {
            val response = apolloClient
                .query(GetShowsByIdQuery(StationsEnum.valueOf(id), first = 10))
                .execute()

            // Check if there are any errors in the response from Apollo
            if (response.hasErrors()) {
                response.errors?.forEach { error ->
                    println("Apollo Error: ${error.message}")
                }
                emptyList()
            } else {
                // Successfully parse the data if there are no errors
                response.data?.shows?.toShowDetailDomain() ?: emptyList()
            }
        } catch (e: Exception) {
            // Handle different types of exceptions here
            println("Error occurred while fetching shows: ${e.message}")
            emptyList() // Return empty list if there is an error
        }
    }

}