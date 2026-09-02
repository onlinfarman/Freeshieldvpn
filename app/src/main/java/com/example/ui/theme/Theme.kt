package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val FreeShieldColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = DarkBackground,
    primaryContainer = DarkSurfaceVariant,
    onPrimaryContainer = CyberCyan,
    secondary = ShieldEmerald,
    onSecondary = DarkBackground,
    secondaryContainer = DarkCardSurface,
    onSecondaryContainer = ShieldEmerald,
    tertiary = ElectricBlue,
    onTertiary = TextPrimary,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder,
    error = CrimsonRed,
    onError = TextPrimary
)

@Composable
fun FreeShieldTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FreeShieldColorScheme,
        typography = Typography,
        content = content
    )
}

