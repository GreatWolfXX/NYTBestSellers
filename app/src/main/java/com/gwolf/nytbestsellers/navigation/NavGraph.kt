package com.gwolf.nytbestsellers.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Screen,
    innerPadding: PaddingValues
) {

    NavHost(
        modifier = Modifier
            .background(Color.White)
            .padding(innerPadding),
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {

        }
        composable<Screen.Lists> {

        }
        composable<Screen.Books> { parameters ->
            val listId = parameters.id
        }
    }
}