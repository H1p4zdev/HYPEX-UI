package com.hypex.toolbox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // ── Header ──
        Text(
            text = "Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )
        Text(
            text = "Device status & quick actions",
            fontSize = 14.sp,
            color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ── Glass Integrity Card ──
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            gradientColors = listOf(HypexPrimary.copy(alpha = 0.12f), HypexSecondary.copy(alpha = 0.06f))
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = HypexPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Play Integrity",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Google Play Services",
                            fontSize = 12.sp,
                            color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IntegrityBadge("BASIC", HypexSuccess)
                    IntegrityBadge("DEVICE", HypexSuccess)
                    IntegrityBadge("STRONG", HypexError)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    color = HypexWarning.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null,
                            tint = HypexWarning,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Bootloader unlocked — STRONG will fail",
                            fontSize = 12.sp,
                            color = HypexWarning,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // ── Quick Actions ──
        Text(
            text = "Quick Actions",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MiuixTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = Icons.Default.VerifiedUser,
                title = "Apply Spoof",
                subtitle = "Last: Pixel 9 Pro",
                color = HypexPrimary,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Default.Security,
                title = "Check Integrity",
                subtitle = "Tap to verify",
                color = HypexSecondary,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = Icons.Default.Delete,
                title = "Debloat",
                subtitle = "Remove bloatware",
                color = HypexWarning,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Default.Speed,
                title = "Performance",
                subtitle = "GPU & RAM tweaks",
                color = HypexAccent,
                modifier = Modifier.weight(1f)
            )
        }

        // ── Active Profile ──
        Text(
            text = "Active Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MiuixTheme.colorScheme.onBackground
        )

        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            gradientColors = listOf(HypexPrimary.copy(alpha = 0.08f), Color.Transparent)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = HypexPrimary.copy(alpha = 0.12f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = HypexPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pixel 9 Pro XL",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Google • komodo • BP1A.250405.005.A1",
                        fontSize = 12.sp,
                        color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 8f
                shape = RoundedCornerShape(20.dp)
                clip = true
            },
        cornerRadius = 20.dp,
        colors = CardDefaults.defaultColors(
            color = MiuixTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (gradientColors.size >= 2) {
                        Modifier.background(
                            brush = Brush.horizontalGradient(colors = gradientColors),
                            shape = RoundedCornerShape(20.dp)
                        )
                    } else Modifier
                )
                .padding(20.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun IntegrityBadge(label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
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
            color = MiuixTheme.colorScheme.surface.copy(alpha = 0.82f)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = color.copy(alpha = 0.15f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}
