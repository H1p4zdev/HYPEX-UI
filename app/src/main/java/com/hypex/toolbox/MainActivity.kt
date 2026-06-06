package com.hypex.toolbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.hypex.toolbox.screens.HomeScreen
import com.hypex.toolbox.screens.SettingsScreen
import com.hypex.toolbox.screens.SpoofingScreen
import com.hypex.toolbox.screens.ToolsScreen
import com.hypex.toolbox.ui.theme.HypexUIToolboxTheme
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarDisplayMode
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.blur.BlendColorEntry
import top.yukonga.miuix.kmp.blur.BlurColors
import top.yukonga.miuix.kmp.blur.drawBackdrop
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.blur.textureBlurEffect
import top.yukonga.miuix.kmp.theme.MiuixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HypexUIToolboxTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val navBackdrop = rememberLayerBackdrop()

    val surfaceColor = MiuixTheme.colorScheme.surface

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Floating glass nav bar with real Miuix Backdrop blur
            Surface(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .graphicsLayer {
                        shadowElevation = 16f
                        shape = RoundedCornerShape(24.dp)
                        clip = true
                    }
                    .drawBackdrop(
                        backdrop = navBackdrop,
                        shape = { RoundedCornerShape(24.dp) },
                        effects = {
                            textureBlurEffect(
                                blurRadiusX = 30f,
                                blurRadiusY = 30f,
                                colors = BlurColors(
                                    blendColors = listOf(
                                        BlendColorEntry(
                                            color = surfaceColor.copy(alpha = 0.55f)
                                        )
                                    )
                                )
                            )
                        }
                    ),
                shape = RoundedCornerShape(24.dp),
                color = surfaceColor.copy(alpha = 0.75f)
            ) {
                NavigationBar(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    color = MiuixTheme.colorScheme.surface.copy(alpha = 0f),
                    showDivider = false,
                    defaultWindowInsetsPadding = false,
                    mode = NavigationBarDisplayMode.IconAndText
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = Icons.Default.Home,
                        label = "Home"
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = Icons.Default.Security,
                        label = "Spoofing"
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = Icons.Default.Build,
                        label = "Tools"
                    )
                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = Icons.Default.Settings,
                        label = "Settings"
                    )
                }
            }
        }
    ) { innerPadding ->
        // Main content area — captured for nav bar backdrop blur
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .then(Modifier.layerBackdrop(navBackdrop)),
            color = MiuixTheme.colorScheme.background
        ) {
            when (selectedTab) {
                0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
                1 -> SpoofingScreen(modifier = Modifier.padding(innerPadding))
                2 -> ToolsScreen(modifier = Modifier.padding(innerPadding))
                3 -> SettingsScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
