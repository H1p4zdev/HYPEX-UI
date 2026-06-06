package com.hypex.toolbox.ui.theme

import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.theme.Colors
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme

// Hypex-UI brand accent colors
val HypexPrimary = Color(0xFF7C4DFF)
val HypexSecondary = Color(0xFF448AFF)
val HypexAccent = Color(0xFF00E5FF)
val HypexBackground = Color(0xFF1A1A2E)
val HypexSurface = Color(0xFF16213E)
val HypexSuccess = Color(0xFF4CAF50)
val HypexWarning = Color(0xFFFFC107)
val HypexError = Color(0xFFEF5350)

// Dark theme extended palette
val DarkBackground = Color(0xFF0D0D1A)
val DarkSurface = Color(0xFF1A1A2E)
val DarkSurfaceVariant = Color(0xFF16213E)
val DarkCard = Color(0xFF1E1E36)
val DarkTextPrimary = Color(0xFFE8E8F0)
val DarkTextSecondary = Color(0xFF9E9EB8)

// Light theme extended palette
val LightBackground = Color(0xFFF5F5FF)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF0EEFF)
val LightCard = Color(0xFFFFFFFF)
val LightTextPrimary = Color(0xFF1A1A2E)
val LightTextSecondary = Color(0xFF6B6B8A)

// Miuix color schemes for dark/light mode
fun hypexDarkColorScheme(): Colors = darkColorScheme(
    primary = HypexPrimary,
    onPrimary = Color.White,
    primaryVariant = Color(0xFF6B3AE0),
    onPrimaryVariant = Color(0xFFD0C0FF),
    error = HypexError,
    onError = Color.White,
    errorContainer = Color(0xFF4A1F1F),
    onErrorContainer = Color(0xFFFFDAD6),
    disabledPrimary = Color(0xFF3A2D5E),
    disabledOnPrimary = Color(0xFF6B6B8A),
    disabledPrimaryButton = Color(0xFF3A2D5E),
    disabledOnPrimaryButton = Color(0xFF6B6B8A),
    disabledPrimarySlider = Color(0xFF4A3D6E),
    primaryContainer = Color(0xFF8E60FF),
    onPrimaryContainer = Color.White,
    secondary = HypexSecondary,
    onSecondary = Color.White,
    secondaryVariant = Color(0xFF3A3A5E),
    onSecondaryVariant = Color(0xFFB0B0D0),
    disabledSecondary = Color(0xFF2E2E4E),
    disabledOnSecondary = Color(0xFF6B6B8A),
    disabledSecondaryVariant = Color(0xFF2E2E4E),
    disabledOnSecondaryVariant = Color(0xFF5A5A7A),
    secondaryContainer = Color(0xFF3A3A5E),
    onSecondaryContainer = Color(0xFF7C7C9E),
    secondaryContainerVariant = Color(0xFF3A3A5E),
    onSecondaryContainerVariant = Color(0xFF8080A0),
    tertiaryContainer = Color(0xFF2B2B4E),
    onTertiaryContainer = HypexPrimary,
    tertiaryContainerVariant = DarkSurface,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    onBackgroundVariant = Color(0xFF787E96),
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceSecondary = Color(0xCCE8E8F0),
    onSurfaceVariantSummary = Color(0x80E8E8F0),
    onSurfaceVariantActions = Color(0x66E8E8F0),
    disabledOnSurface = Color(0xFF5A5A7A),
    surfaceContainer = DarkCard,
    onSurfaceContainer = DarkTextPrimary,
    onSurfaceContainerVariant = Color(0xFF8080A0),
    surfaceContainerHigh = DarkSurfaceVariant,
    onSurfaceContainerHigh = Color(0xFF6B6B8A),
    surfaceContainerHighest = Color(0xFF252545),
    onSurfaceContainerHighest = Color(0xFFD0D0E8),
    outline = Color(0xFF343454),
    dividerLine = Color(0xFF2A2A4A),
    windowDimming = Color.Black.copy(alpha = 0.6f),
    sliderKeyPoint = Color(0x4D7A8AA6),
    sliderKeyPointForeground = Color(0xFF9E70FF),
    sliderBackground = Color(0x26FFFFFF)
)

fun hypexLightColorScheme(): Colors = lightColorScheme(
    primary = HypexPrimary,
    onPrimary = Color.White,
    primaryVariant = HypexPrimary,
    onPrimaryVariant = Color(0xFFD0C0FF),
    error = HypexError,
    onError = Color.White,
    errorContainer = Color(0xFFFDF6F4),
    onErrorContainer = Color(0xFF410002),
    disabledPrimary = Color(0xFFC2B0FF),
    disabledOnPrimary = Color(0xFFF3F0FF),
    disabledPrimaryButton = Color(0xFFC2B0FF),
    disabledOnPrimaryButton = Color.White,
    disabledPrimarySlider = Color(0xFFB8A8F5),
    primaryContainer = Color(0xFF9E70FF),
    onPrimaryContainer = Color.White,
    secondary = HypexSecondary,
    onSecondary = Color.White,
    secondaryVariant = Color(0xFFE8E8F0),
    onSecondaryVariant = Color(0xFF3A3A5E),
    disabledSecondary = Color(0xFFF0F0F8),
    disabledOnSecondary = Color(0xFFA8A8C0),
    disabledSecondaryVariant = Color(0xFFF0F0F8),
    disabledOnSecondaryVariant = Color(0xFFB0B0C8),
    secondaryContainer = Color(0xFFE8E8F0),
    onSecondaryContainer = Color(0xFF7C7C9E),
    secondaryContainerVariant = Color(0xFFE8E8F0),
    onSecondaryContainerVariant = Color(0xFF8080A0),
    tertiaryContainer = Color(0xFFEEF0FF),
    onTertiaryContainer = HypexPrimary,
    tertiaryContainerVariant = LightSurface,
    background = LightBackground,
    onBackground = LightTextPrimary,
    onBackgroundVariant = Color(0xFF8C93B0),
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceSecondary = Color(0xCC1A1A2E),
    onSurfaceVariantSummary = Color(0x991A1A2E),
    onSurfaceVariantActions = Color(0x661A1A2E),
    disabledOnSurface = Color(0xFFB0B0C8),
    surfaceContainer = LightCard,
    onSurfaceContainer = LightTextPrimary,
    onSurfaceContainerVariant = Color(0xFF8080A0),
    surfaceContainerHigh = LightSurfaceVariant,
    onSurfaceContainerHigh = Color(0xFF6B6B8A),
    surfaceContainerHighest = Color(0xFFE8E8F0),
    onSurfaceContainerHighest = LightTextPrimary,
    outline = Color(0xFFD8D8E8),
    dividerLine = Color(0xFFE0E0EE),
    windowDimming = Color.Black.copy(alpha = 0.3f),
    sliderKeyPoint = Color(0x4DA3B3CD),
    sliderKeyPointForeground = Color(0xFF9E70FF),
    sliderBackground = Color(0x0F000000)
)
