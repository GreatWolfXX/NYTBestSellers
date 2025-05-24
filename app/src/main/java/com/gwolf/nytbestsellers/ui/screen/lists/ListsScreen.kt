package com.gwolf.nytbestsellers.ui.screen.lists

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.domain.entity.ListEntity
import com.gwolf.nytbestsellers.navigation.Screen
import com.gwolf.nytbestsellers.ui.component.ErrorComponent
import com.gwolf.nytbestsellers.ui.component.LoadingComponent
import com.gwolf.nytbestsellers.ui.theme.BackgroundColor

@Composable
fun ListsScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: ListsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState(initial = ListsEvent.Idle)

    LaunchedEffect(event) {
        when (val data: ListsEvent = event) {
            is ListsEvent.Idle -> {}
            is ListsEvent.Navigate -> {
                navController.navigate(
                    Screen.Books(
                        listId = data.listId,
                        listName = data.listName
                    )
                )
            }
        }
    }

    ListsContent(
        innerPadding = innerPadding,
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )

    LoadingComponent(state.isLoading)
}

@Composable
private fun ListsContent(
    innerPadding: PaddingValues,
    state: ListsScreenState,
    onIntent: (ListsIntent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Crossfade(targetState = state.error != null) { hasError ->
            if (hasError) {
                ErrorComponent(
                    error = state.error,
                    onClick = {
                        onIntent(ListsIntent.Refresh)
                    }
                )
            } else {
                ListsMainSection(
                    innerPadding = innerPadding,
                    state = state,
                    onIntent = onIntent
                )
            }
        }
    }
}

@Composable
private fun ListsMainSection(
    innerPadding: PaddingValues,
    state: ListsScreenState,
    onIntent: (ListsIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(state = state)
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                key = { list -> list.listId },
                items = state.listsList
            ) { list ->
                ListItem(
                    item = list
                ) {
                    onIntent(
                        ListsIntent.ItemClick(
                            listId = list.listId,
                            listName = list.displayName
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(
    state: ListsScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.title_list_categories),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = stringResource(
                R.string.title_publication_date,
                state.result?.publishedDate.orEmpty()
            ),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Composable
private fun ListItem(
    item: ListEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = item.displayName,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun ListItemPreview() {
    val item = ListEntity(
        resultBestsellersDate = "24.05.2025",
        listId = 0,
        displayName = "List Name"
    )

    ListItem(
        item = item,
        onClick = { }
    )
}

@Preview
@Composable
private fun ListsScreenPreview() {
    ListsContent(
        innerPadding = PaddingValues(),
        state = ListsScreenState()
    )
}