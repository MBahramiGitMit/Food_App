package com.mbahrami.foodapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColor = darkColors(
    primary = purple_200,
    secondary = teal_200,
    primaryVariant = purple_700
)

private val LightColor = lightColors(
    primary = purple_500,
    secondary = teal_700,
    primaryVariant = purple_500

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FoodAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColor else LightColor

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shape,
        content = content
    )
}