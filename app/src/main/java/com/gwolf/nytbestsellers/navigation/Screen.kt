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
        val listId: Int,
        val listName: String
    ) : Screen()

    @Serializable
    data class WebView(
        val uri: String
    ) : Screen()
}
