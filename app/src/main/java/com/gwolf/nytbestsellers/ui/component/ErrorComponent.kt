package com.gwolf.nytbestsellers.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gwolf.nytbestsellers.R
import com.gwolf.nytbestsellers.util.AppError

@Composable
fun ErrorComponent(
    error: AppError?,
    onClick: () -> Unit
) {
    val errorDesc = when (error) {
        is AppError.SessionNotExist -> {
            stringResource(R.string.err_session_not_exist)
        }

        is AppError.Unexpected -> {
            stringResource(R.string.err_unexpected)
        }

        is AppError.FailedRetrieveData -> {
            stringResource(R.string.err_failed_retrieve_data)
        }

        is AppError.UnknownHostException -> {
            stringResource(R.string.err_unable_connect)
        }

        is AppError.NoCredential -> {
            stringResource(R.string.err_auth_failed)

        }

        else -> {
            stringResource(R.string.err_unexpected)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.title_error),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier,
                text = errorDesc,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Button(
            modifier = Modifier
                .wrapContentWidth(),
            elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 16.dp),
            onClick = onClick
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.refresh),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorComponentPreview() {
    ErrorComponent(
        error = AppError.SessionNotExist,
        onClick = {}
    )
}