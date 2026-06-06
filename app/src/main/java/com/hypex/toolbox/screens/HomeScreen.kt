package com.hypex.toolbox.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hypex.toolbox.ui.theme.HypexAccent
import com.hypex.toolbox.ui.theme.HypexError
import com.hypex.toolbox.ui.theme.HypexPrimary
import com.hypex.toolbox.ui.theme.HypexSecondary
import com.hypex.toolbox.ui.theme.HypexSuccess
import com.hypex.toolbox.ui.theme.HypexWarning
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var checkResult by remember { mutableStateOf("Checking...") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // ── Hero Header ──
        Text(
            text = "Good evening,",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Text(
            text = "Hypex Toolbox",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // ── Hero Integrity Card ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 12f
                    shape = RoundedCornerShape(24.dp)
                    clip = true
                },
            cornerRadius = 24.dp,
            colors = CardDefaults.defaultColors(
                color = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                HypexPrimary.copy(alpha = 0.25f),
                                HypexPrimary.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = HypexSuccess.copy(alpha = 0.15f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = HypexSuccess,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Play Integrity",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Google Play Services",
                                fontSize = 13.sp,
                                color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Animated status badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatusChip("BASIC", HypexSuccess, true)
                        StatusChip("DEVICE", HypexSuccess, true)
                        StatusChip("STRONG", HypexError, false)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Warning banner
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp)),
                        color = HypexWarning.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Android,
                                contentDescription = null,
                                tint = HypexWarning,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Bootloader unlocked — STRONG unavailable",
                                fontSize = 12.sp,
                                color = HypexWarning,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Re-check button
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { checkResult = "Checking..." }
                            .clip(RoundedCornerShape(14.dp)),
                        color = HypexPrimary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = HypexPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Check Integrity",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HypexPrimary
                            )
                        }
                    }
                }
            }
        }

        // ── Quick Actions — horizontal pill row ──
        Text(
            text = "Quick Actions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(quickActions) { action ->
                PillActionCard(action = action)
            }
        }

        // ── Active Profile ──
        Text(
            text = "Active Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )

        ActiveProfileCard()

        // ── Quick Stats ──
        Text(
            text = "Device Info",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                label = "Android Version",
                value = "15",
                color = HypexSecondary,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Security Patch",
                value = "June 2026",
                color = HypexSuccess,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

private data class QuickAction(
    val icon: ImageVector,
    val label: String,
    val color: Color
)

private val quickActions = listOf(
    QuickAction(Icons.Default.VerifiedUser, "Apply Spoof", HypexPrimary),
    QuickAction(Icons.Default.Security, "Check", HypexSecondary),
    QuickAction(Icons.Default.Delete, "Debloat", HypexWarning),
    QuickAction(Icons.Default.Speed, "Optimize", HypexAccent),
    QuickAction(Icons.Default.Build, "Props", Color(0xFFA66CFF)),
    QuickAction(Icons.Default.ArrowForward, "More", Color(0xFF78909C))
)

@Composable
private fun RowScope.StatusChip(label: String, color: Color, active: Boolean) {
    val bgColor by animateColorAsState(
        targetValue = if (active) color.copy(alpha = 0.12f) else MiuixTheme.colorScheme.surface.copy(alpha = 0.4f),
        animationSpec = tween(600),
        label = "statusBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (active) color else MiuixTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        animationSpec = tween(600),
        label = "statusText"
    )
    Surface(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp)),
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (active) color else Color.Transparent)
            )
        }
    }
}

@Composable
private fun PillActionCard(action: QuickAction) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { },
        shape = RoundedCornerShape(20.dp),
        color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(12.dp),
                color = action.color.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        tint = action.color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = action.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MiuixTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ActiveProfileCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shadowElevation = 6f
                shape = RoundedCornerShape(20.dp)
                clip = true
            },
        cornerRadius = 20.dp,
        colors = CardDefaults.defaultColors(
            color = MiuixTheme.colorScheme.surface.copy(alpha = 0.75f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(16.dp),
                color = HypexPrimary.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = HypexPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Pixel 9 Pro XL",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Google • komodo • BP1A.250405.005.A1",
                    fontSize = 12.sp,
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                )
            }
            Surface(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { },
                shape = RoundedCornerShape(10.dp),
                color = HypexPrimary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Switch",
                        tint = HypexPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 4f
                shape = RoundedCornerShape(16.dp)
                clip = true
            },
        cornerRadius = 16.dp,
        colors = CardDefaults.defaultColors(
            color = MiuixTheme.colorScheme.surface.copy(alpha = 0.75f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(10.dp),
                color = color.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MiuixTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
