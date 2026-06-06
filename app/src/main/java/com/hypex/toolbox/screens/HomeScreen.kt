package com.hypex.toolbox.screens

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hypex.toolbox.ui.theme.HypexAccent
import com.hypex.toolbox.ui.theme.HypexError
import com.hypex.toolbox.ui.theme.HypexPrimary
import top.yukonga.miuix.kmp.utils.overScrollHorizontal
import top.yukonga.miuix.kmp.utils.scrollEndHaptic
import com.hypex.toolbox.ui.theme.HypexSecondary
import com.hypex.toolbox.ui.theme.HypexSuccess
import com.hypex.toolbox.ui.theme.HypexWarning
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

// ── Real device info using Android Build class ──
private data class DeviceInfo(
    val model: String = Build.MODEL,
    val manufacturer: String = Build.MANUFACTURER,
    val brand: String = Build.BRAND,
    val board: String = Build.BOARD,
    val fingerprint: String = Build.FINGERPRINT,
    val display: String = Build.DISPLAY,
    val androidVersion: String = Build.VERSION.RELEASE,
    val securityPatch: String = Build.VERSION.SECURITY_PATCH,
    val buildType: String = Build.TYPE,
    val tags: String = Build.TAGS,
    val bootloader: String = Build.BOOTLOADER,
    val sdkInt: Int = Build.VERSION.SDK_INT,
)

// ── Integrity check result ──
private data class IntegrityResult(
    val basicPassed: Boolean,
    val devicePassed: Boolean,
    val strongPassed: Boolean,
    val details: List<String>,
    val isBootloaderUnlocked: Boolean,
)

private fun checkIntegrity(): IntegrityResult {
    val deviceInfo = DeviceInfo()
    val details = mutableListOf<String>()
    var bootloaderUnlocked = false

    // Check 1: BASIC — device has verified boot / release-keys
    val hasReleaseKeys = deviceInfo.tags.contains("release-keys", ignoreCase = true)
    val basicPassed = hasReleaseKeys

    if (!hasReleaseKeys) {
        details.add("Build tags: ${deviceInfo.tags} (expected release-keys)")
    }

    // Check 2: DEVICE — proper user build
    val isUserBuild = deviceInfo.buildType == "user"
    val devicePassed = hasReleaseKeys && isUserBuild

    if (!isUserBuild) {
        details.add("Build type: ${deviceInfo.buildType} (expected user)")
    }

    // Check 3: STRONG — typically requires verified boot state (locked bootloader)
    // We approximate by checking if bootloader is locked
    bootloaderUnlocked = try {
        val bootloaderState = deviceInfo.bootloader
        // If bootloader is empty or contains a version, it may be locked.
        // An unlocked bootloader often has "unlocked" in the string or is non-empty with "unknown"
        bootloaderState.contains("unlocked", ignoreCase = true) ||
            bootloaderState.isBlank() ||
            bootloaderState.contains("unknown", ignoreCase = true)
    } catch (_: Exception) {
        true // assume unlocked if we can't determine
    }

    val strongPassed = devicePassed && !bootloaderUnlocked

    if (bootloaderUnlocked) {
        details.add("Bootloader may be unlocked")
    }

    return IntegrityResult(
        basicPassed = basicPassed,
        devicePassed = devicePassed,
        strongPassed = strongPassed,
        details = details,
        isBootloaderUnlocked = bootloaderUnlocked,
    )
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(checkIntegrity()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .scrollEndHaptic()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // ── Hero Header ──
        Text(
            text = "Device Overview",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )
        Text(
            text = "${Build.MANUFACTURER} ${Build.MODEL}",
            fontSize = 15.sp,
            color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        // ── Hero Integrity Card (fixed: uses proper Miuix surfaceContainer instead of transparent) ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 8f
                    shape = RoundedCornerShape(24.dp)
                    clip = true
                },
            cornerRadius = 24.dp,
            insideMargin = PaddingValues(0.dp),
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                HypexPrimary.copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = RoundedCornerShape(14.dp),
                            color = HypexSuccess.copy(alpha = 0.12f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = HypexSuccess,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Play Integrity",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "API ${Build.VERSION.SDK_INT}",
                                fontSize = 12.sp,
                                color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Animated status badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatusChip("BASIC", if (result.basicPassed) HypexSuccess else HypexWarning, result.basicPassed)
                        StatusChip("DEVICE", if (result.devicePassed) HypexSuccess else HypexWarning, result.devicePassed)
                        StatusChip("STRONG", if (result.strongPassed) HypexSuccess else HypexError, result.strongPassed)
                    }

                    if (result.details.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        result.details.forEach { detail ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                color = HypexWarning.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = HypexWarning,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = detail,
                                        fontSize = 12.sp,
                                        color = HypexWarning.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        }
                    }

                    // Bootloader warning
                    if (result.isBootloaderUnlocked) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            color = HypexWarning.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
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
                                    text = "Bootloader may be unlocked",
                                    fontSize = 12.sp,
                                    color = HypexWarning,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Miuix Button for re-check
                    Button(
                        onClick = { result = checkIntegrity() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColorsPrimary(
                            color = HypexPrimary
                        ),
                        cornerRadius = 14.dp,
                        insideMargin = PaddingValues(vertical = 10.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Check Integrity",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // ── Quick Actions ──
        SmallTitle(text = "Quick Actions")

        LazyRow(
            modifier = Modifier.overScrollHorizontal(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(quickActions) { action ->
                PillActionCard(action = action)
            }
        }

        // ── Device Info details──
        SmallTitle(text = "Device Details")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 4f
                    shape = RoundedCornerShape(20.dp)
                    clip = true
                },
            cornerRadius = 20.dp,
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
            )
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                DetailRow("Model", Build.MODEL)
                DetailRow("Manufacturer", Build.MANUFACTURER)
                DetailRow("Brand", Build.BRAND)
                DetailRow("Android", "API ${Build.VERSION.SDK_INT} · ${Build.VERSION.RELEASE}")
                DetailRow("Security Patch", Build.VERSION.SECURITY_PATCH)
                DetailRow("Build Type", Build.TYPE)
                DetailRow("Tags", Build.TAGS)
                DetailRow("Bootloader", Build.BOOTLOADER)
                DetailRow("Display", Build.DISPLAY)
            }
        }

        // ── Active Profile (real device info, no dummy data) ──
        SmallTitle(text = "Active Profile")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 4f
                    shape = RoundedCornerShape(20.dp)
                    clip = true
                },
            cornerRadius = 20.dp,
            colors = CardDefaults.defaultColors(
                color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(50.dp),
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
                        text = Build.MODEL,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${Build.MANUFACTURER} · ${Build.BOARD} · ${Build.DISPLAY.take(30)}",
                        fontSize = 11.sp,
                        color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                        maxLines = 1,
                    )
                }
                // Miuix IconButton for refresh
                IconButton(
                    onClick = { result = checkIntegrity() },
                    backgroundColor = HypexPrimary.copy(alpha = 0.1f),
                    cornerRadius = 12.dp,
                    minWidth = 36.dp,
                    minHeight = 36.dp,
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = HypexPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // ── Jumplinks row (replaces StatCard with useful links) ──
        SmallTitle(text = "Jump To")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            JumpCard(
                label = "Spoofing Profiles",
                value = "Spoof device identity",
                icon = Icons.Default.Security,
                color = HypexSecondary,
                modifier = Modifier.weight(1f)
            )
            JumpCard(
                label = "System Tools",
                value = "Props, debloat, more",
                icon = Icons.Default.Build,
                color = HypexPrimary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ── Data ──

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

// ── Reusable composables ──

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
            .clip(RoundedCornerShape(10.dp)),
        color = bgColor,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
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
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (active) color else Color.Transparent)
            )
        }
    }
}

@Composable
private fun PillActionCard(action: QuickAction) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(34.dp),
                shape = RoundedCornerShape(10.dp),
                color = action.color.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        tint = action.color,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = action.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MiuixTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 13.sp,
            color = MiuixTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun JumpCard(
    label: String,
    value: String,
    icon: ImageVector,
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
        insideMargin = PaddingValues(0.dp),
        colors = CardDefaults.defaultColors(
            color = MiuixTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(10.dp),
                color = color.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MiuixTheme.colorScheme.onSurface
                )
                Text(
                    text = value,
                    fontSize = 11.sp,
                    color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}
