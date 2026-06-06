package com.hypex.toolbox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
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
import com.hypex.toolbox.ui.theme.HypexPrimary
import com.hypex.toolbox.ui.theme.HypexSecondary
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.HorizontalDivider
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var darkModeEnabled by remember { mutableStateOf(true) }
    var autoUpdateEnabled by remember { mutableStateOf(true) }
    var betaChannelEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // ── App Header Card ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 8f
                    shape = RoundedCornerShape(24.dp)
                    clip = true
                },
            cornerRadius = 24.dp,
            colors = CardDefaults.defaultColors(color = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                HypexPrimary.copy(alpha = 0.15f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = HypexPrimary.copy(alpha = 0.15f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                tint = HypexPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Hypex-UI Toolbox",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MiuixTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Version 1.0.0",
                        fontSize = 14.sp,
                        color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Preferences ──
        SectionHeader(title = "Preferences", icon = Icons.Default.DarkMode, color = HypexPrimary)

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 18.dp,
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
            )
        ) {
            Column {
                SettingsSwitchItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    subtitle = "Follow system theme",
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    accentColor = Color(0xFF7C4DFF)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                )
                SettingsSwitchItem(
                    icon = Icons.Default.Sync,
                    title = "Auto Update",
                    subtitle = "Check for fingerprint updates",
                    checked = autoUpdateEnabled,
                    onCheckedChange = { autoUpdateEnabled = it },
                    accentColor = Color(0xFF448AFF)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                )
                SettingsSwitchItem(
                    icon = Icons.Default.BugReport,
                    title = "Beta Channel",
                    subtitle = "Receive pre-release updates",
                    checked = betaChannelEnabled,
                    onCheckedChange = { betaChannelEnabled = it },
                    accentColor = Color(0xFFFF6B6B)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Data ──
        SectionHeader(title = "Data", icon = Icons.Default.Storage, color = HypexSecondary)

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 18.dp,
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
            )
        ) {
            Column {
                SettingsClickItem(
                    icon = Icons.Default.Storage,
                    title = "Export Config",
                    subtitle = "Save spoofing profiles & settings",
                    accentColor = HypexSecondary
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                )
                SettingsClickItem(
                    icon = Icons.Default.Share,
                    title = "Import Config",
                    subtitle = "Load profiles from backup file",
                    accentColor = HypexAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── About ──
        SectionHeader(title = "About", icon = Icons.Default.Info, color = Color(0xFFA66CFF))

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 18.dp,
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
            )
        ) {
            Column {
                SettingsClickItem(
                    icon = Icons.Default.Info,
                    title = "About Hypex-UI Toolbox",
                    subtitle = "Version 1.0.0 • Build 1",
                    accentColor = HypexPrimary
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                )
                SettingsClickItem(
                    icon = Icons.Default.Language,
                    title = "GitHub",
                    subtitle = "github.com/hypex-ui/toolbox",
                    accentColor = Color(0xFF24292E)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Footer ──
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = "Made with ❤️ for HyperOS",
                fontSize = 13.sp,
                color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.25f),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Hypex-UI v1.0.0",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.15f)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionHeader(
    title: String,
    icon: ImageVector,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(28.dp),
            shape = RoundedCornerShape(8.dp),
            color = color.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(12.dp),
            color = accentColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.45f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsClickItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(12.dp),
            color = accentColor.copy(alpha = 0.12f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.45f)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.15f),
            modifier = Modifier.size(20.dp)
        )
    }
}
