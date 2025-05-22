package com.gwolf.nytbestsellers.domain.entity

data class BookEntity(
    val listId: Int,
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    val bookImage: String,
    val rank: Int,
    val amazonProductUrl: String,
    val primaryIsbn13: String,
)