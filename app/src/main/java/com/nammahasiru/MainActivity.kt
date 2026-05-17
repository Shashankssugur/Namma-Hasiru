package com.nammahasiru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.nammahasiru.ui.AppRoot
import com.nammahasiru.ui.theme.NammaHasiruTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammaHasiruTheme(darkTheme = isSystemInDarkTheme()) {
                AppSurface {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
private fun AppSurface(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        content()
    }
}

