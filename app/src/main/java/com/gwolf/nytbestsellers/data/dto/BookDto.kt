package com.gwolf.nytbestsellers.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    @SerialName("book_image") val bookImage: String,
    val rank: Int,
    @SerialName("amazon_product_url") val amazonProductUrl: String,
    @SerialName("primary_isbn13") val primaryIsbn13: String,
)