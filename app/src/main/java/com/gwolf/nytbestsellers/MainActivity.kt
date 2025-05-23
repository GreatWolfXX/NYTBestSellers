package com.gwolf.nytbestsellers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gwolf.nytbestsellers.ui.theme.NYTBestSellersTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepSplashOnScreen = true
        installSplashScreen().apply {
            setKeepOnScreenCondition { keepSplashOnScreen }
        }
        enableEdgeToEdge()

        setContent {
            val screen by splashViewModel.state.collectAsState()

            LaunchedEffect(screen) {
                if (screen != null) {
                    keepSplashOnScreen = false
                }
            }

            NYTBestSellersTheme {

            }
        }
    }
}
