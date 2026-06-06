package com.hypex.toolbox.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

private data class ToolItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val category: String
)

private data class ToolCategory(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val tools: List<ToolItem>
)

private val toolCategories = listOf(
    ToolCategory(
        name = "Spoofing", icon = Icons.Default.HealthAndSafety, color = HypexSuccess, tools = listOf(
            ToolItem("Play Integrity Fix", "Patch fingerprint", Icons.Default.HealthAndSafety, HypexSuccess, "Spoofing"),
            ToolItem("Keybox Manager", "Manage keyboxes", Icons.Default.ContentCopy, HypexSecondary, "Spoofing")
        )
    ),
    ToolCategory(
        name = "System", icon = Icons.Default.Dns, color = HypexPrimary, tools = listOf(
            ToolItem("Build Prop Editor", "Edit system props", Icons.Default.Dns, HypexPrimary, "System"),
            ToolItem("Smart Debloater", "Remove bloatware", Icons.Default.Handyman, HypexWarning, "System"),
            ToolItem("MSA Killer", "Disable ad engine", Icons.Default.Code, HypexError, "System")
        )
    ),
    ToolCategory(
        name = "Debug", icon = Icons.Default.Terminal, color = HypexAccent, tools = listOf(
            ToolItem("Logcat Viewer", "Capture system logs", Icons.Default.Terminal, HypexAccent, "Debug")
        )
    ),
    ToolCategory(
        name = "Tools", icon = Icons.Default.Description, color = Color(0xFFFF6B6B), tools = listOf(
            ToolItem("Payload Dumper", "Extract payload.bin", Icons.Default.Description, Color(0xFFFF6B6B), "Tools"),
            ToolItem("Screenshot", "Screen capture tool", Icons.Default.Screenshot, Color(0xFFA66CFF), "Tools")
        )
    )
)

@Composable
fun ToolsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Toolbox",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.onBackground
        )
        Text(
            text = "All-in-one system modding tools",
            fontSize = 14.sp,
            color = MiuixTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(toolCategories) { category ->
                Column {
                    // Category header
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = category.color.copy(alpha = 0.12f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = null,
                                    tint = category.color,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = category.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MiuixTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = category.color.copy(alpha = 0.1f)
                        ) {
                            Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                                Text(
                                    text = "${category.tools.size}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = category.color
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tools in this category
                    category.tools.forEachIndexed { index, tool ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        ToolCard(tool = tool)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun ToolCard(tool: ToolItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .graphicsLayer {
                shadowElevation = 4f
                shape = RoundedCornerShape(18.dp)
                clip = true
            },
        cornerRadius = 18.dp,
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
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(14.dp),
                color = tool.color.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = tool.icon,
                        contentDescription = null,
                        tint = tool.color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tool.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        color = tool.color.copy(alpha = 0.1f)
                    ) {
                        Box(modifier = Modifier.padding(horizontal = 7.dp, vertical = 1.dp)) {
                            Text(
                                text = tool.category,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = tool.color
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tool.description,
                        fontSize = 12.sp,
                        color = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Handyman,
                contentDescription = null,
                tint = MiuixTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
