package com.gwolf.nytbestsellers.ui.screen.webview

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.ui.component.ErrorComponent
import com.gwolf.nytbestsellers.ui.theme.BackgroundColor

@Composable
fun WebViewScreen(
    navController: NavController,
    viewModel: WebViewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState(initial = WebViewEvent.Idle)

    LaunchedEffect(event) {
        when (event) {
            is WebViewEvent.Idle -> {}
            is WebViewEvent.NavigateBack -> {
                navController.navigateUp()
            }
        }
    }

    WebViewContent(
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )
}

@Composable
private fun WebViewContent(
    state: WebViewScreenState,
    onIntent: (WebViewIntent) -> Unit = {}
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
                        onIntent(WebViewIntent.Refresh)
                    }
                )
            } else {
                WebViewMainSection(
                    state = state,
                    onIntent = onIntent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebViewMainSection(
    state: WebViewScreenState,
    onIntent: (WebViewIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            title = {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onIntent(WebViewIntent.NavigateBack)
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = BackgroundColor
            )
        )
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                }
            }, update = {
                it.loadUrl(state.uri)
            }
        )
    }
}

@Preview
@Composable
private fun WebViewScreenPreview() {
    WebViewContent(
        state = WebViewScreenState()
    )
}