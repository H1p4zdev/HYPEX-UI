package com.hypex.toolbox.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun HypexUIToolboxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) hypexDarkColorScheme() else hypexLightColorScheme()

    MiuixTheme(
        colors = colors,
        content = content
    )
}
