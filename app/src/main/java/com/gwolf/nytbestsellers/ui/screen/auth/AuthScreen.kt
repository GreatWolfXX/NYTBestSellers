package com.gwolf.nytbestsellers.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.navigation.Screen
import com.gwolf.nytbestsellers.ui.component.ErrorComponent
import com.gwolf.nytbestsellers.ui.component.LoadingComponent
import com.gwolf.nytbestsellers.ui.theme.BackgroundColor

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState(initial = AuthEvent.Idle)

    LaunchedEffect(event) {
        when (event) {
            is AuthEvent.Idle -> {}
            is AuthEvent.Navigate -> {
                navController.navigate(Screen.Lists)
            }
        }
    }

    AuthContent(
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )

    LoadingComponent(state.isLoading)
}

@Composable
private fun AuthContent(
    state: AuthScreenState,
    onIntent: (AuthIntent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.error != null) {
            ErrorComponent(
                error = state.error,
                onClick = {

                }
            )
        } else {
            AuthMainSection(
                onIntent = onIntent
            )
        }
    }
}

@Composable
private fun AuthMainSection(
    onIntent: (AuthIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            painter = painterResource(R.drawable.splashscreen_ic),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        GoogleButton {
            onIntent(AuthIntent.Auth)
        }
    }
}

@Composable
private fun GoogleButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .wrapContentWidth(),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.google_ic),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun GoogleButtonPreview() {
    GoogleButton { }
}

@Preview
@Composable
private fun CartScreenPreview() {
    AuthContent(
        state = AuthScreenState()
    )
}