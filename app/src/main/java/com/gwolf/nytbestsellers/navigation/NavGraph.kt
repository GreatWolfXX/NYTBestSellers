package com.gwolf.nytbestsellers.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gwolf.nytbestsellers.ui.screen.auth.AuthScreen
import com.gwolf.nytbestsellers.ui.screen.lists.ListsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Screen,
    innerPadding: PaddingValues
) {

    NavHost(
        modifier = Modifier
            .background(Color.White),
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {
            AuthScreen(
                navController = navController
            )
        }
        composable<Screen.Lists> {
            ListsScreen(
                navController = navController,
                innerPadding = innerPadding
            )
        }
        composable<Screen.Books> { parameters ->
            val listId = parameters.id
        }
    }
}