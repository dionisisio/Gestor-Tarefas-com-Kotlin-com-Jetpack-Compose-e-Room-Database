package gestortarefas.bambi.eduardo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Esquema de cores para tema claro
private val LightColorScheme = lightColorScheme(
    primary = BlueLight,
    secondary = BlueSecondary,
    tertiary = BlueTertiary,
    background = SurfaceWhite,
    surface = SurfaceWhite,
    onPrimary = Color.White,
    onSecondary = TextBlack,
    onTertiary = TextBlack,
    onBackground = TextBlack,
    onSurface = TextBlack,
)

// Esquema de cores para tema escuro
private val DarkColorScheme = darkColorScheme(
    primary = BlueDark,
    secondary = BlueLight,
    tertiary = BlueTertiary,
    background = BackgroundGray,
    surface = BlueDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun AnotacoesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
