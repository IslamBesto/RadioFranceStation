package com.example.radiofrancestation.data

import com.example.GetBrandByIdQuery
import com.example.GetBrandsQuery
import com.example.radiofrancestation.domain.model.BrandDetailDomain
import com.example.radiofrancestation.domain.model.BrandDomain

fun GetBrandByIdQuery.Brand.toBrandDetailDomain() = BrandDetailDomain(
    id = id,
    title = title,
    description = description,
    baseLine = baseline
)

fun GetBrandsQuery.Brand.toBrandDomain() = BrandDomain(
    id = id,
    title = title,
    baseLine = baseline
)