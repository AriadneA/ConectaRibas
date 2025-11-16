package com.example.conectaribas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VerdeAguaDark,
    secondary = VerdeFolhaDark,
    tertiary = LaranjaSuaveDark,
    background = CinzaEscuro,
    surface = CinzaEscuro,
    onPrimary = Branco,
    onSecondary = Branco,
    onTertiary = Branco,
    onBackground = CremeDark,
    onSurface = CremeDark
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeAgua,
    secondary = VerdeFolha,
    tertiary = LaranjaSuave,
    background = Creme,
    surface = Branco,
    onPrimary = Branco,
    onSecondary = Branco,
    onTertiary = Branco,
    onBackground = CinzaEscuro,
    onSurface = CinzaEscuro
)

@Composable
fun ConectaRibasTheme(
    darkTheme: Boolean = false,
    // Dynamic color desabilitado para usar nossa paleta amazônica personalizada
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}