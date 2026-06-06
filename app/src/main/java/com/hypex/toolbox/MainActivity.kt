package com.hypex.toolbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.hypex.toolbox.screens.HomeScreen
import com.hypex.toolbox.screens.SettingsScreen
import com.hypex.toolbox.screens.SpoofingScreen
import com.hypex.toolbox.screens.ToolsScreen
import com.hypex.toolbox.ui.theme.HypexPrimary
import com.hypex.toolbox.ui.theme.HypexUIToolboxTheme
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarDisplayMode
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Surface
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
    val surfaceColor = MiuixTheme.colorScheme.surface

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // ── Floating glass nav bar with hardware-accelerated texture ──
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .graphicsLayer {
                        shadowElevation = 28f
                        shape = RoundedCornerShape(32.dp)
                        clip = true
                        ambientShadowColor = Color(0xFF7C4DFF).copy(alpha = 0.20f)
                        spotShadowColor = Color(0xFF7C4DFF).copy(alpha = 0.25f)
                    }
            ) {
                // Gradient edge glow behind the nav bar surface
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .drawBehind {
                            val w = size.width
                            // Top gradient edge — subtle light sweep
                            drawRoundRect(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        HypexPrimary.copy(alpha = 0.03f),
                                        Color.Transparent,
                                        Color.Transparent,
                                        HypexPrimary.copy(alpha = 0.03f)
                                    )
                                ),
                                cornerRadius = CornerRadius(32.dp.toPx()),
                                style = Stroke(width = 2.dp.toPx())
                            )
                        }
                )

                // Main glass surface
                Surface(
                    modifier = Modifier
                        .graphicsLayer {
                            shadowElevation = 0f
                            shape = RoundedCornerShape(32.dp)
                            clip = true
                        }
                        .drawBehind {
                            // Premium dashed glass border
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.08f),
                                cornerRadius = CornerRadius(32.dp.toPx()),
                                style = Stroke(
                                    width = 1.2f.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(14f, 10f),
                                        0f
                                    )
                                )
                            )
                        },
                    shape = RoundedCornerShape(32.dp),
                    color = surfaceColor.copy(alpha = 0.55f)
                ) {
                    NavigationBar(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        color = Color.Transparent,
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
        }
    ) { innerPadding ->
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
