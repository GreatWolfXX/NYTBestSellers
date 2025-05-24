package com.gwolf.nytbestsellers.ui.screen.books

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.domain.entity.BookEntity
import com.gwolf.nytbestsellers.ui.component.ErrorComponent
import com.gwolf.nytbestsellers.ui.component.LoadingComponent
import com.gwolf.nytbestsellers.ui.theme.BackgroundColor

@Composable
fun BooksScreen(
    innerPadding: PaddingValues,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState(initial = BooksEvent.Idle)

    LaunchedEffect(event) {
        when (event) {
            is BooksEvent.Idle -> {}
            is BooksEvent.OpenLink -> {

            }
        }
    }

    BooksContent(
        innerPadding = innerPadding,
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )

    LoadingComponent(state.isLoading)
}

@Composable
private fun BooksContent(
    innerPadding: PaddingValues,
    state: BooksScreenState,
    onIntent: (BooksIntent) -> Unit = {}
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
                        onIntent(BooksIntent.Refresh)
                    }
                )
            } else {
                BooksMainSection(
                    innerPadding = innerPadding,
                    state = state,
                    onIntent = onIntent
                )
            }
        }
    }
}

@Composable
private fun BooksMainSection(
    innerPadding: PaddingValues,
    state: BooksScreenState,
    onIntent: (BooksIntent) -> Unit
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
                key = { book -> book.primaryIsbn13 },
                items = state.booksList
            ) { book ->
                BookItem(
                    item = book
                ) {
                    onIntent(BooksIntent.ItemClick(uri = book.amazonProductUrl))
                }
            }
        }
    }
}

@Composable
private fun Title(
    state: BooksScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = state.listName,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun BookItem(
    item: BookEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.8f),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .wrapContentHeight(),
                model = item.bookImage,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier,
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                LabeledText(
                    label = stringResource(R.string.title_author),
                    value = item.author
                )
                LabeledText(
                    label = stringResource(R.string.title_publisher),
                    value = item.publisher
                )
                LabeledText(
                    label = stringResource(R.string.title_isbn13),
                    value = item.primaryIsbn13
                )
                LabeledText(
                    label = stringResource(R.string.title_rank),
                    value = item.rank.toString()
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    modifier = Modifier,
                    text = item.description,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        GoToBuyButton(onClick = onClick)
    }
}

@Composable
fun LabeledText(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                append(label)
            }
            append(" ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                append(value)
            }
        },
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Black
    )
}

@Composable
fun GoToBuyButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black
        ),
        contentPadding = PaddingValues(8.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.go_buy),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun BooksItemPreview() {
    val item = BookEntity(
        listId = 0,
        title = "Book",
        description = "description",
        author = "author",
        publisher = "publisher",
        bookImage = "",
        rank = 1,
        amazonProductUrl = "",
        primaryIsbn13 = ""
    )

    BookItem(
        item = item,
        onClick = { }
    )
}

@Preview
@Composable
private fun GoToBuyButtonPreview() {
    GoToBuyButton { }
}

@Preview
@Composable
private fun BooksScreenPreview() {
    BooksContent(
        innerPadding = PaddingValues(),
        state = BooksScreenState()
    )
}