package com.hypex.toolbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hypex.toolbox.screens.HomeScreen
import com.hypex.toolbox.screens.SettingsScreen
import com.hypex.toolbox.screens.SpoofingScreen
import com.hypex.toolbox.screens.ToolsScreen
import com.hypex.toolbox.ui.theme.HypexUIToolboxTheme
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.blur.BlendColorEntry
import top.yukonga.miuix.kmp.blur.BlurBlendMode
import top.yukonga.miuix.kmp.blur.BlurDefaults
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.blur.textureBlur
import top.yukonga.miuix.kmp.blur.highlight.Highlight
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

    // Step 1: Create a LayerBackdrop to capture background content for the nav bar blur
    // Draw an opaque background rect to prevent color artifacts from transparency
    val backgroundColor = MiuixTheme.colorScheme.background
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Step 3: Apply textureBlur on the nav bar using the captured backdrop
            // This produces real frosted glass — content behind the nav bar is blurred
            Box(
                modifier = Modifier
                    .textureBlur(
                        backdrop = backdrop,
                        shape = RoundedCornerShape(28.dp),
                        blurRadius = 48f,
                        colors = BlurDefaults.blurColors(
                            blendColors = listOf(
                                BlendColorEntry(
                                    color = MiuixTheme.colorScheme.surface.copy(alpha = 0.55f),
                                    mode = BlurBlendMode.SrcOver
                                )
                            ),
                            saturation = 1.2f,
                            brightness = 0.02f
                        ),
                        highlight = Highlight.GlassStrokeMiddleDark
                    )
            ) {
                FloatingNavigationBar(
                    color = Color.Transparent
                ) {
                    FloatingNavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = Icons.Default.Home,
                        label = "Home"
                    )
                    FloatingNavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = Icons.Default.Security,
                        label = "Spoofing"
                    )
                    FloatingNavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = Icons.Default.Build,
                        label = "Tools"
                    )
                    FloatingNavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        icon = Icons.Default.Settings,
                        label = "Settings"
                    )
                }
            }
        }
    ) { innerPadding ->
        // Step 2: Capture the content area as the backdrop source
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MiuixTheme.colorScheme.background
            ) {
                Crossfade(
                    targetState = selectedTab,
                    animationSpec = tween(durationMillis = 300)
                ) { tab ->
                    when (tab) {
                        0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
                        1 -> SpoofingScreen(modifier = Modifier.padding(innerPadding))
                        2 -> ToolsScreen(modifier = Modifier.padding(innerPadding))
                        3 -> SettingsScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
