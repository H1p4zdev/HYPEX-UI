package com.hypex.toolbox.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hypex.toolbox.ui.theme.HypexAccent
import com.hypex.toolbox.ui.theme.HypexPrimary
import com.hypex.toolbox.ui.theme.HypexSuccess
import com.hypex.toolbox.ui.theme.HypexWarning
data class DeviceProfile(
    val name: String,
    val brand: String,
    val manufacturer: String,
    val model: String,
    val fingerprint: String,
    val product: String
)

private val deviceProfiles = listOf(
    DeviceProfile("Pixel 8 Pro", "Google", "Google", "Pixel 8 Pro", "google/husky/husky:15/BP1A.250405.005.B1/13151136:user/release-keys", "husky"),
    DeviceProfile("Pixel 9 Pro XL", "Google", "Google", "Pixel 9 Pro XL", "google/komodo/komodo:15/BP1A.250405.005.A1/13151424:user/release-keys", "komodo"),
    DeviceProfile("Pixel 9 Pro", "Google", "Google", "Pixel 9 Pro", "google/caiman_beta/caiman:CinnamonBun/CP31.260423.012.A1/15327290:user/release-keys", "caiman_beta"),
    DeviceProfile("Galaxy S24 Ultra", "Samsung", "Samsung", "SM-S928B", "samsung/SM-S928B/SM-S928B:14/UP1A.231005.007/20240225:user/release-keys", "SM-S928B"),
    DeviceProfile("Galaxy S25 Ultra", "Samsung", "samsung", "SM-S938B", "samsung/pa3qxxx/pa3q:15/AP3A.240905.015.A2/S938BXXU1AYB4:user/release-keys", "pa3qxxx"),
    DeviceProfile("Xiaomi 14 Ultra", "Xiaomi", "Xiaomi", "24030PN60G", "Xiaomi/aurora_eea/aurora:14/UKQ1.240523.001/OS2.0.102.0.VNAEUXM:user/release-keys", "aurora"),
    DeviceProfile("Xiaomi 15 Ultra", "Xiaomi", "Xiaomi", "25010PN30G", "Xiaomi/xuanyuan_eea/xuanyuan:15/AQ3A.240912.001/OS2.0.10.0.VOAEUXM:user/release-keys", "xuanyuan"),
    DeviceProfile("OnePlus 13", "OnePlus", "OnePlus", "PJD110", "OnePlus/PJD110/OP5D0DL1:15/AP3A.240617.008/V.1bd19a1-1-2:user/release-keys", "PJD110"),
    DeviceProfile("ROG Phone 9", "ASUS", "asus", "ASUSAI2501", "asus/WWAI2501/ASUSAI2501:15/AQ3A.240829.003/35.1810.1810.346-0:user/release-keys", "ASUSAI2501"),
    DeviceProfile("RedMagic 10 Pro", "Nubia", "ZTE", "NX789J", "nubia/NX789J-UN/NX789J:15/AQ3A.240812.002/20241212.194919:user/release-keys", "NX789J"),
    DeviceProfile("Realme 15 Pro 5G", "realme", "realme", "RMX5101", "realme/RMX5101IN/RE60B4L1:15/AP3A.240617.008/V.R4T2.26cec0e-80bb4e-80b757:user/release-keys", "RMX5101")
)

private val categories = listOf("All", "Google", "Samsung", "Xiaomi", "Gaming")

private fun brandColor(brand: String): Color = when (brand.lowercase()) {
    "google" -> Color(0xFF4285F4)
    "samsung" -> Color(0xFF1428A0)
    "xiaomi" -> Color(0xFFFF6900)
    "oneplus" -> Color(0xFFEB0028)
    "asus" -> Color(0xFF00A8E1)
    "nubia", "zte" -> Color(0xFFCC0000)
    "realme" -> Color(0xFFFFC300)
    else -> HypexPrimary
}

@Composable
fun SpoofingScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableIntStateOf(0) }
    var selectedDeviceIndex by remember { mutableIntStateOf(-1) }
    var isApplying by remember { mutableStateOf(false) }

    val filteredProfiles = remember(searchQuery, selectedCategory) {
        deviceProfiles.filter { profile ->
            val matchesSearch = searchQuery.isEmpty() || profile.name.contains(searchQuery, ignoreCase = true) ||
                    profile.manufacturer.contains(searchQuery, ignoreCase = true) ||
                    profile.model.contains(searchQuery, ignoreCase = true)
            val matchesCategory = when (categories[selectedCategory]) {
                "All" -> true
                "Google" -> profile.brand.equals("Google", ignoreCase = true)
                "Samsung" -> profile.brand.equals("Samsung", ignoreCase = true)
                "Xiaomi" -> profile.brand.equals("Xiaomi", ignoreCase = true)
                "Gaming" -> profile.brand in listOf("ASUS", "Nubia")
                else -> true
            }
            matchesSearch && matchesCategory
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // ── Header ──
        Text(
            text = "Spoofing Profiles",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "${deviceProfiles.size} devices available",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Search Bar ──
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text("Search devices...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Category Chips ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEachIndexed { index, category ->
                val isSelected = selectedCategory == index
                val chipColor by animateColorAsState(
                    targetValue = if (isSelected) HypexPrimary else MaterialTheme.colorScheme.surface,
                    animationSpec = tween(300)
                )
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = index },
                    label = {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = chipColor,
                        selectedContainerColor = HypexPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = HypexPrimary.copy(alpha = 0.3f),
                        selectedBorderColor = HypexPrimary,
                        enabled = true,
                        selected = isSelected
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Device List ──
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(filteredProfiles) { profile ->
                val index = deviceProfiles.indexOf(profile)
                val isSelected = selectedDeviceIndex == index
                DeviceProfileCard(
                    profile = profile,
                    isSelected = isSelected,
                    isApplying = isApplying && isSelected,
                    onClick = {
                        selectedDeviceIndex = if (isSelected) -1 else index
                    },
                    onApply = {
                        isApplying = true
                        // TODO: Implement actual spoof apply logic
                    }
                )
            }

            if (filteredProfiles.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No devices found",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceProfileCard(
    profile: DeviceProfile,
    isSelected: Boolean,
    isApplying: Boolean,
    onClick: () -> Unit,
    onApply: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) HypexPrimary else Color.Transparent,
        animationSpec = tween(300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                HypexPrimary.copy(alpha = 0.05f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
                .background(
                    color = borderColor.takeIf { isSelected } ?: Color.Transparent,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand icon
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brandColor(profile.brand).copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = brandColor(profile.brand),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = profile.manufacturer,
                            fontSize = 12.sp,
                            color = brandColor(profile.brand),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = " • ${profile.model}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }

                // Selected checkmark or apply button
                if (isSelected) {
                    if (isApplying) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = HypexPrimary
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(HypexSuccess.copy(alpha = 0.15f))
                                .clickable { onApply() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Apply",
                                tint = HypexSuccess,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
