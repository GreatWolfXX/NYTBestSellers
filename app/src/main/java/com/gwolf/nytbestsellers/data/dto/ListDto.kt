package com.gwolf.nytbestsellers.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListDto(
    @SerialName("display_name") val displayName: String,
    val books: List<BookDto>
)