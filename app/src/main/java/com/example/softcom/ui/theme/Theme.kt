package com.example.softcom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.softcom.loja.ui.theme.BackgroundColor
import com.softcom.loja.ui.theme.BackgroundDarkColor
import com.softcom.loja.ui.theme.OnPrimaryColor
import com.softcom.loja.ui.theme.OnPrimaryDarkColor
import com.softcom.loja.ui.theme.OnSecondaryColor
import com.softcom.loja.ui.theme.OnSecondaryDarkColor
import com.softcom.loja.ui.theme.OnSurfaceColor
import com.softcom.loja.ui.theme.OnSurfaceDarkColor
import com.softcom.loja.ui.theme.PrimaryColor
import com.softcom.loja.ui.theme.PrimaryDarkColor
import com.softcom.loja.ui.theme.SecondaryColor
import com.softcom.loja.ui.theme.SecondaryDarkColor
import com.softcom.loja.ui.theme.Shapes
import com.softcom.loja.ui.theme.SurfaceColor
import com.softcom.loja.ui.theme.SurfaceDarkColor
import com.softcom.loja.ui.theme.Typography

// Definir esquema de cores claro
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkColor,
    onPrimary = OnPrimaryDarkColor,
    secondary = SecondaryDarkColor,
    onSecondary = OnSecondaryDarkColor,
    background = BackgroundDarkColor,
    surface = SurfaceDarkColor,
    onSurface = OnSurfaceDarkColor,
)

@Composable
fun LojaVirtualTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false 
    )


    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
