package com.example.radiofrancestation.data

import com.example.GetBrandsQuery
import com.example.GetShowsByIdQuery
import com.example.radiofrancestation.domain.model.BrandDomain
import com.example.radiofrancestation.domain.model.ShowDomain

fun GetShowsByIdQuery.Shows.toShowDetailDomain(): List<ShowDomain> {
    return edges?.mapNotNull { edge ->
        edge?.node?.let { node ->
            val episodeCount = node.diffusionsConnection?.edges?.size ?: 0

            ShowDomain(
                title = node.title,
                episodeCount = episodeCount
            )
        }
    } ?: emptyList()
}


fun GetBrandsQuery.Brand.toBrandDomain() = BrandDomain(
    id = id,
    title = title,
    baseLine = baseline
)