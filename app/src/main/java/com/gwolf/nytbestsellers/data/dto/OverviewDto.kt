package com.gwolf.nytbestsellers.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverviewDto(
    val status: String,
    val results: ResultDto
)

@Serializable
data class ResultDto(
    @SerialName("bestsellers_date") val bestsellersDate: String,
    @SerialName("published_date") val publishedDate: String,
    val lists: List<ListDto>
)