#!/bin/bash
set -euo pipefail

# ============================================================
# Hypex-UI Toolbox - Jetpack Compose Project Generator
# ============================================================
# This script generates a complete empty Jetpack Compose
# Android project ready for Hypex-UI Toolbox development.
# ============================================================

echo "========================================"
echo "  Generating Jetpack Compose Project..."
echo "========================================"

# ── Configuration from environment ──────────────────────────
APP_NAME="${APP_NAME:-HypexUI-Toolbox}"
PACKAGE_NAME="${PACKAGE_NAME:-com.hypex.toolbox}"
PACKAGE_PATH="${PACKAGE_NAME//.//}"

MIN_SDK="${MIN_SDK:-26}"
TARGET_SDK="${TARGET_SDK:-35}"
COMPILE_SDK="${COMPILE_SDK:-35}"

AGP_VERSION="${AGP_VERSION:-9.2.0}"
KOTLIN_VERSION="${KOTLIN_VERSION:-2.4.0}"

# Derive Gradle version from AGP version
GRADLE_VERSION="9.5.1"
COMPOSE_BOM_VERSION="2026.05.00"
COMPILE_SDK_INT=$COMPILE_SDK

echo "  App name:    $APP_NAME"
echo "  Package:     $PACKAGE_NAME"
echo "  SDK:         min=$MIN_SDK target=$TARGET_SDK compile=$COMPILE_SDK"
echo "  AGP:         $AGP_VERSION"
echo "  Kotlin:      $KOTLIN_VERSION"
echo "  Gradle:      $GRADLE_VERSION"
echo ""

# ── Helper Function ────────────────────────────────────────
create_file() {
    local file="$1"
    local dir
    dir=$(dirname "$file")
    mkdir -p "$dir"
    echo "  📄 $file"
    cat > "$file"
}

# ════════════════════════════════════════════════════════════
#  1. ROOT PROJECT FILES
# ════════════════════════════════════════════════════════════

echo ""
echo "── Creating root project files ──"

# 1a. settings.gradle.kts
create_file "settings.gradle.kts" <<- 'SETTINGS_EOF'
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HypexUI-Toolbox"
include(":app")
SETTINGS_EOF

# 1b. Root build.gradle.kts
create_file "build.gradle.kts" <<- 'ROOT_BUILD_EOF'
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
ROOT_BUILD_EOF

# 1c. gradle.properties
create_file "gradle.properties" <<- 'GRADLE_PROPS_EOF'
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
#org.gradle.configuration-cache=true
GRADLE_PROPS_EOF

# 1d. version catalog (libs.versions.toml)
create_file "gradle/libs.versions.toml" <<- 'CATALOG_EOF'
[versions]
agp = "AGP_VERSION_PLACEHOLDER"
kotlin = "KOTLIN_VERSION_PLACEHOLDER"
coreKtx = "1.19.0"
lifecycleRuntime = "2.11.0"
activityCompose = "1.13.0"
composeBom = "COMPOSE_BOM_PLACEHOLDER"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntime" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
CATALOG_EOF

# Replace placeholders in catalog
sed -i "s/AGP_VERSION_PLACEHOLDER/$AGP_VERSION/g" gradle/libs.versions.toml
sed -i "s/KOTLIN_VERSION_PLACEHOLDER/$KOTLIN_VERSION/g" gradle/libs.versions.toml
sed -i "s/COMPOSE_BOM_PLACEHOLDER/$COMPOSE_BOM_VERSION/g" gradle/libs.versions.toml

# 1e. gradle-wrapper.properties
create_file "gradle/wrapper/gradle-wrapper.properties" <<- 'WRAPPER_EOF'
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-GRADLE_VERSION_PLACEHOLDER-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
WRAPPER_EOF
sed -i "s/GRADLE_VERSION_PLACEHOLDER/$GRADLE_VERSION/g" gradle/wrapper/gradle-wrapper.properties

# 1f. .gitignore
create_file ".gitignore" <<- 'GITIGNORE_EOF'
*.iml
.gradle
/local.properties
/.idea
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
local.properties
/app/build
app/build/
*.apk
*.aab
*.jks
*.keystore
GITIGNORE_EOF

echo "  ✅ Root project files created (Gradle wrapper will be generated by the workflow)"

# ════════════════════════════════════════════════════════════
#  2. APP MODULE
# ════════════════════════════════════════════════════════════

echo ""
echo "── Creating app module ──"

# 2a. app/build.gradle.kts
create_file "app/build.gradle.kts" <<- 'APP_BUILD_EOF'
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "PACKAGE_PLACEHOLDER"
    compileSdk = COMPILE_SDK_PLACEHOLDER

    defaultConfig {
        applicationId = "PACKAGE_PLACEHOLDER"
        minSdk = MIN_SDK_PLACEHOLDER
        targetSdk = TARGET_SDK_PLACEHOLDER
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
APP_BUILD_EOF

# Replace placeholders
sed -i "s/PACKAGE_PLACEHOLDER/$PACKAGE_NAME/g" app/build.gradle.kts
sed -i "s/COMPILE_SDK_PLACEHOLDER/$COMPILE_SDK/g" app/build.gradle.kts
sed -i "s/MIN_SDK_PLACEHOLDER/$MIN_SDK/g" app/build.gradle.kts
sed -i "s/TARGET_SDK_PLACEHOLDER/$TARGET_SDK/g" app/build.gradle.kts

# 2b. proguard-rules.pro
create_file "app/proguard-rules.pro" <<- 'PROGUARD_EOF'
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the SDK tools.

-keepattributes Signature
-keepattributes *Annotation*

# Keep Hypex-UI Toolbox data models
-keep class com.hypex.toolbox.data.** { *; }
PROGUARD_EOF

# 2c. AndroidManifest.xml
create_file "app/src/main/AndroidManifest.xml" <<- 'MANIFEST_EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.HypexUIToolbox">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.HypexUIToolbox">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
MANIFEST_EOF

# 2d. MainActivity.kt
create_file "app/src/main/java/$PACKAGE_PATH/MainActivity.kt" <<- 'MAIN_ACTIVITY_EOF'
package PACKAGE_PLACEHOLDER

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import PACKAGE_PLACEHOLDER.ui.theme.HypexUIToolboxTheme

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Security, contentDescription = "Spoofing") },
                    label = { Text("Spoofing") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Build, contentDescription = "Tools") },
                    label = { Text("Tools") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
            1 -> SpoofingScreen(modifier = Modifier.padding(innerPadding))
            2 -> ToolsScreen(modifier = Modifier.padding(innerPadding))
            3 -> SettingsScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // TODO: Home screen with device status, integrity check, quick actions
}

@Composable
fun SpoofingScreen(modifier: Modifier = Modifier) {
    // TODO: Spoofing profiles, per-app spoofing, fingerprint management
}

@Composable
fun ToolsScreen(modifier: Modifier = Modifier) {
    // TODO: Tools like payload dumper, debloater, etc.
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    // TODO: App settings, about, updates
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HypexUIToolboxTheme {
        MainScreen()
    }
}
MAIN_ACTIVITY_EOF

sed -i "s/PACKAGE_PLACEHOLDER/$PACKAGE_NAME/g" "app/src/main/java/$PACKAGE_PATH/MainActivity.kt"

# 2e. UI Theme files
create_file "app/src/main/java/$PACKAGE_PATH/ui/theme/Color.kt" <<- 'COLOR_EOF'
package PACKAGE_PLACEHOLDER.ui.theme

import androidx.compose.ui.graphics.Color

// Primary - Deep Purple (Hypex brand)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650A4)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

// Hypex-UI accent colors
val HypexPrimary = Color(0xFF7C4DFF)
val HypexSecondary = Color(0xFF448AFF)
val HypexBackground = Color(0xFF1A1A2E)
COLOR_EOF

sed -i "s/PACKAGE_PLACEHOLDER/$PACKAGE_NAME/g" "app/src/main/java/$PACKAGE_PATH/ui/theme/Color.kt"

create_file "app/src/main/java/$PACKAGE_PATH/ui/theme/Type.kt" <<- 'TYPE_EOF'
package PACKAGE_PLACEHOLDER.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
TYPE_EOF

sed -i "s/PACKAGE_PLACEHOLDER/$PACKAGE_NAME/g" "app/src/main/java/$PACKAGE_PATH/ui/theme/Type.kt"

create_file "app/src/main/java/$PACKAGE_PATH/ui/theme/Theme.kt" <<- 'THEME_EOF'
package PACKAGE_PLACEHOLDER.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun HypexUIToolboxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
THEME_EOF

sed -i "s/PACKAGE_PLACEHOLDER/$PACKAGE_NAME/g" "app/src/main/java/$PACKAGE_PATH/ui/theme/Theme.kt"

# 2f. Android Resources
create_file "app/src/main/res/values/strings.xml" <<- 'STRINGS_EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">APP_NAME_PLACEHOLDER</string>
    <string name="app_description">Hypex-UI Toolbox - Spoofing &amp; System Tools</string>
</resources>
STRINGS_EOF
sed -i "s/APP_NAME_PLACEHOLDER/$APP_NAME/g" app/src/main/res/values/strings.xml

create_file "app/src/main/res/values/colors.xml" <<- 'COLORS_XML_EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
    <color name="hypex_primary">#FF7C4DFF</color>
    <color name="hypex_secondary">#FF448AFF</color>
    <color name="hypex_background">#FF1A1A2E</color>
</resources>
COLORS_XML_EOF

create_file "app/src/main/res/values/themes.xml" <<- 'THEMES_XML_EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.HypexUIToolbox" parent="android:Theme.Material.Light.NoActionBar" />
</resources>
THEMES_XML_EOF

# 2g. Launcher icons (adaptive icon)
create_file "app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml" <<- 'IC_LAUNCHER_EOF'
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/hypex_primary"/>
    <foreground android:drawable="@color/white"/>
</adaptive-icon>
IC_LAUNCHER_EOF

create_file "app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml" <<- 'IC_LAUNCHER_ROUND_EOF'
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/hypex_primary"/>
    <foreground android:drawable="@color/white"/>
</adaptive-icon>
IC_LAUNCHER_ROUND_EOF

echo "  ✅ App module created"

# ════════════════════════════════════════════════════════════
#  3. TOOLBOX DATA FILES
# ════════════════════════════════════════════════════════════

echo ""
echo "── Creating toolbox data files ──"

# 3a. Device model database (from Kaorios reference)
create_file "toolbox-data/device-model.json" <<- 'DEVICES_EOF'
{
  "devices": [
    {
      "name": "Pixel 8 Pro",
      "BRAND": "google",
      "DEVICE": "husky",
      "MANUFACTURER": "Google",
      "MODEL": "Pixel 8 Pro",
      "FINGERPRINT": "google/husky/husky:15/BP1A.250405.005.B1/13151136:user/release-keys",
      "PRODUCT": "husky"
    },
    {
      "name": "Pixel 9 Pro XL",
      "BRAND": "google",
      "DEVICE": "komodo",
      "MANUFACTURER": "Google",
      "MODEL": "Pixel 9 Pro XL",
      "FINGERPRINT": "google/komodo/komodo:15/BP1A.250405.005.A1/13151424:user/release-keys",
      "PRODUCT": "komodo"
    },
    {
      "name": "Pixel 9 Pro",
      "BRAND": "google",
      "DEVICE": "caiman",
      "MANUFACTURER": "Google",
      "MODEL": "Pixel 9 Pro",
      "FINGERPRINT": "google/caiman_beta/caiman:CinnamonBun/CP31.260423.012.A1/15327290:user/release-keys",
      "PRODUCT": "caiman_beta"
    },
    {
      "name": "Samsung Galaxy S24 Ultra",
      "BRAND": "Samsung",
      "DEVICE": "Galaxy S24 Ultra",
      "MANUFACTURER": "Samsung",
      "MODEL": "SM-S928B",
      "FINGERPRINT": "samsung/SM-S928B/SM-S928B:14/UP1A.231005.007/20240225:user/release-keys",
      "PRODUCT": "SM-S928B"
    },
    {
      "name": "Samsung Galaxy S25 Ultra",
      "BRAND": "samsung",
      "DEVICE": "Samsung Galaxy S25 Ultra",
      "MANUFACTURER": "samsung",
      "MODEL": "SM-S938B",
      "FINGERPRINT": "samsung/pa3qxxx/pa3q:15/AP3A.240905.015.A2/S938BXXU1AYB4:user/release-keys",
      "PRODUCT": "pa3qxxx"
    },
    {
      "name": "Xiaomi 14 Ultra",
      "BRAND": "Xiaomi",
      "DEVICE": "Xiaomi 14 Ultra",
      "MANUFACTURER": "Xiaomi",
      "MODEL": "24030PN60G",
      "FINGERPRINT": "Xiaomi/aurora_eea/aurora:14/UKQ1.240523.001/OS2.0.102.0.VNAEUXM:user/release-keys",
      "PRODUCT": "aurora"
    },
    {
      "name": "Xiaomi 15 Ultra",
      "BRAND": "Xiaomi",
      "DEVICE": "Xiaomi 15 Ultra",
      "MANUFACTURER": "Xiaomi",
      "MODEL": "25010PN30G",
      "FINGERPRINT": "Xiaomi/xuanyuan_eea/xuanyuan:15/AQ3A.240912.001/OS2.0.10.0.VOAEUXM:user/release-keys",
      "PRODUCT": "xuanyuan"
    },
    {
      "name": "OnePlus 13",
      "BRAND": "OnePlus",
      "DEVICE": "OnePlus 13",
      "MANUFACTURER": "OnePlus",
      "MODEL": "PJD110",
      "FINGERPRINT": "OnePlus/PJD110/OP5D0DL1:15/AP3A.240617.008/V.1bd19a1-1-2:user/release-keys",
      "PRODUCT": "PJD110"
    },
    {
      "name": "ASUS ROG Phone 9",
      "BRAND": "asus",
      "DEVICE": "ROG Phone 9",
      "MANUFACTURER": "asus",
      "MODEL": "ASUSAI2501",
      "FINGERPRINT": "asus/WWAI2501/ASUSAI2501:15/AQ3A.240829.003/35.1810.1810.346-0:user/release-keys",
      "PRODUCT": "ASUSAI2501"
    },
    {
      "name": "RedMagic 10 Pro",
      "BRAND": "nubia",
      "DEVICE": "RedMagic 10 Pro",
      "MANUFACTURER": "ZTE",
      "MODEL": "NX789J",
      "FINGERPRINT": "nubia/NX789J-UN/NX789J:15/AQ3A.240812.002/20241212.194919:user/release-keys",
      "PRODUCT": "NX789J"
    },
    {
      "name": "Realme 15 Pro 5G",
      "BRAND": "realme",
      "DEVICE": "Realme 15 Pro 5G",
      "MANUFACTURER": "realme",
      "MODEL": "RMX5101",
      "FINGERPRINT": "realme/RMX5101IN/RE60B4L1:15/AP3A.240617.008/V.R4T2.26cec0e-80bb4e-80b757:user/release-keys",
      "PRODUCT": "RMX5101"
    }
  ]
}
DEVICES_EOF

# 3b. Sample PIF props
create_file "toolbox-data/pif-props.json" <<- 'PIF_EOF'
{
  "BRAND": "google",
  "DEVICE": "komodo",
  "FINGERPRINT": "google/komodo/komodo:15/BP1A.250405.005.A1/13151424:user/release-keys",
  "ID": "BP1A.250405.005.A1",
  "MANUFACTURER": "Google",
  "MODEL": "Pixel 9 Pro XL",
  "PRODUCT": "komodo",
  "RELEASE": "15",
  "SECURITY_PATCH": "2025-04-05",
  "TAGS": "release-keys",
  "TYPE": "user"
}
PIF_EOF

# 3c. Sample per-app spoofing rules
create_file "toolbox-data/app-props.json" <<- 'APPS_EOF'
{
  "com.google.android.gms": {
    "MANUFACTURER": "Google",
    "MODEL": "Pixel 9 Pro XL",
    "FINGERPRINT": "google/komodo/komodo:15/BP1A.250405.005.A1/13151424:user/release-keys"
  },
  "com.netflix.mediaclient": {
    "MANUFACTURER": "Google",
    "MODEL": "Pixel 9 Pro XL"
  },
  "com.google.android.apps.photos": {
    "MANUFACTURER": "Google",
    "MODEL": "Pixel 9 Pro XL"
  }
}
APPS_EOF

# 3d. README for toolbox-data
create_file "toolbox-data/README.md" <<- 'DATA_README_EOF'
# Toolbox Data Files

These JSON files are the configuration database for the Hypex-UI Toolbox app.

- `device-model.json` — Device spoofing profiles (fingerprints, models)
- `pif-props.json` — Active Play Integrity Fix fingerprint
- `app-props.json` — Per-app spoofing rules

The app reads these files and your patched framework reads the config at runtime.
DATA_README_EOF

echo "  ✅ Toolbox data files created"

# ════════════════════════════════════════════════════════════
#  4. FRAMEWORK PATCHES
# ════════════════════════════════════════════════════════════

echo ""
echo "── Creating framework patch files ──"

create_file "framework-patches/README.md" <<- 'FRAMEWORK_README_EOF'
# Hypex-UI Framework Patches

These are the patches needed for your Hypex-UI custom ROM's framework
to support native spoofing (without Xposed/Magisk modules).

## Files to patch in AOSP / HyperOS source:

1. `frameworks/base/core/java/android/os/SystemProperties.java`
   - Add hook to read spoofed props from /data/system/hypex/pif.json

2. `frameworks/base/core/java/android/os/Build.java`
   - Override Build.MODEL, Build.FINGERPRINT, etc. via config

3. `frameworks/base/services/core/java/com/android/server/pm/PackageManagerService.java`
   - Signature spoofing support for microG

## Implementation details:

The patched framework reads JSON config files written by the Hypex-UI Toolbox app.
When any app calls Build.MODEL or SystemProperties.get("ro.build.fingerprint"),
the framework checks if a spoofed value exists for that app and returns it.

See individual patch files for exact code changes.
FRAMEWORK_README_EOF

create_file "framework-patches/SystemProperties.patch" <<- 'SYS_PROPS_PATCH_EOF'
--- a/core/java/android/os/SystemProperties.java
+++ b/core/java/android/os/SystemProperties.java
@@ -16,12 +16,62 @@

 package android.os;

+import java.io.File;
+import java.io.FileReader;
+import java.util.HashMap;
+import org.json.JSONObject;
+import org.json.JSONTokener;
+
 public class SystemProperties {
 
     private static final int PROP_NAME_MAX = 31;
     private static final int PROP_VALUE_MAX = 91;
 
+    // Hypex-UI Spoofing: cache of spoofed properties
+    private static HashMap<String, String> sSpoofedProps = null;
+    private static boolean sSpoofInitDone = false;
+
+    private static void initSpoofing() {
+        if (sSpoofInitDone) return;
+        sSpoofInitDone = true;
+        sSpoofedProps = new HashMap<>();
+        try {
+            File configFile = new File("/data/system/hypex/pif.json");
+            if (configFile.exists()) {
+                FileReader reader = new FileReader(configFile);
+                JSONObject json = new JSONObject(new JSONTokener(reader));
+                reader.close();
+
+                // Map PIF props to system properties
+                putIfExists(json, "FINGERPRINT", "ro.build.fingerprint");
+                putIfExists(json, "MODEL", "ro.product.model");
+                putIfExists(json, "MANUFACTURER", "ro.product.manufacturer");
+                putIfExists(json, "BRAND", "ro.product.brand");
+                putIfExists(json, "PRODUCT", "ro.product.name");
+                putIfExists(json, "DEVICE", "ro.product.device");
+                putIfExists(json, "SECURITY_PATCH", "ro.build.version.security_patch");
+            }
+        } catch (Exception e) {
+            // Config not found - use real values
+        }
+    }
+
+    private static void putIfExists(JSONObject json, String key, String prop) {
+        if (json.has(key)) {
+            sSpoofedProps.put(prop, json.optString(key));
+        }
+    }
+
+    // Hook into the native get method
+    public static String get(String key, String def) {
+        initSpoofing();
+        if (sSpoofedProps != null && sSpoofedProps.containsKey(key)) {
+            return sSpoofedProps.get(key);
+        }
+        return native_get(key, def);
+    }
+
     // Original native methods (kept intact)
+    private static native String native_get(String key, String def);
 }
SYS_PROPS_PATCH_EOF

echo "  ✅ Framework patch files created"

# ════════════════════════════════════════════════════════════
#  5. SUMMARY
# ════════════════════════════════════════════════════════════

echo ""
echo "========================================"
echo "  ✅ Project generation complete!"
echo "========================================"
echo ""
echo "  Generated files:"
echo "  ├── Root project files"
echo "  │   ├── build.gradle.kts"
echo "  │   ├── settings.gradle.kts"
echo "  │   ├── gradle.properties"
echo "  │   ├── gradle/libs.versions.toml"
echo "  │   └── gradle/wrapper/gradle-wrapper.properties"
echo "  ├── App module"
echo "  │   ├── app/build.gradle.kts"
echo "  │   ├── app/src/main/AndroidManifest.xml"
echo "  │   └── app/src/main/java/$PACKAGE_PATH/"
echo "  │       ├── MainActivity.kt"
echo "  │       └── ui/theme/"
echo "  ├── Toolbox data"
echo "  │   ├── device-model.json     ($(wc -l < toolbox-data/device-model.json) lines)"
echo "  │   ├── pif-props.json"
echo "  │   └── app-props.json"
echo "  └── Framework patches"
echo "      └── framework-patches/"
echo ""
echo "  Next steps:"
echo "  1. Clone this repo to your local machine"
echo "  2. Open in Android Studio"
echo "  3. Start building the Hypex-UI Toolbox features!"
echo ""
echo "========================================"
