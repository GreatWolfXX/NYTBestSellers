package com.gwolf.nytbestsellers.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Auth : Screen()

    @Serializable
    data object Lists : Screen()

    @Serializable
    data class Books(
        val listId: Int
    ) : Screen()

    @Serializable
    data class BookInfo(
        val isbn: String
    ) : Screen()
}
