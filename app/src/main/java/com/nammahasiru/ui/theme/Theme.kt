package com.nammahasiru.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = GreenLeaf,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = SoilBrown,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    background = Sand,
    onBackground = androidx.compose.ui.graphics.Color(0xFF1B1B1B),
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color(0xFF1B1B1B)
)

private val DarkColors = darkColorScheme(
    primary = GreenLight,
    onPrimary = androidx.compose.ui.graphics.Color(0xFF0B1F0D),
    secondary = Color(0xFFD7CCC8),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF1B1B1B),
    background = Color(0xFF0F1A12),
    onBackground = Color(0xFFE8F5E9),
    surface = Color(0xFF142018),
    onSurface = Color(0xFFE8F5E9)
)

@Composable
fun NammaHasiruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            window.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

