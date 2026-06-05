# Hypex-UI Toolbox 🧰

**Spoofing & System Tools for Hypex-UI Custom ROM**  
*Based on Xiaomi HyperOS 3*

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🎭 **Play Integrity Spoofing** | One-click fingerprint spoofing for BASIC + DEVICE integrity |
| 📱 **Device Spoofing** | Spoof model, brand, manufacturer, build props |
| ⚙️ **Per-App Spoofing** | Different spoof profiles for different apps |
| 🔑 **KeyStore Spoofing** | TrickyStore keybox management for STRONG integrity |
| 📍 **Region Spoofer** | Switch between Global/China/India/EU |
| 🧹 **Debloater** | Remove HyperOS bloatware safely |
| 🚫 **Ad Removal** | MSA killer + system-wide ad blocking |
| 🎮 **Game Tools** | FPS unlock, GPU driver updater, config editor |
| ☁️ **Google Photos** | Pixel spoof for unlimited backup |
| 📊 **Device Dashboard** | Hardware info, sensors, integrity status |

---

## 🚀 How It Works

Hypex-UI Toolbox is a **companion app** for the Hypex-UI custom ROM. It works by:

1. **App writes JSON configs** → `/data/system/hypex/` directory
2. **Patched framework reads configs** → `SystemProperties.java` and `Build.java` are patched at ROM compile time
3. **Spoofing happens natively** → No Xposed, no Magisk modules needed

This means the spoofing is **undetectable** because it IS the system.

---

## 🏗️ Project Structure

```
Hypex-UI-Toolbox/
├── .github/
│   └── workflows/
│       └── generate-project.yml    # GitHub Action to scaffold project
├── app/
│   ├── src/main/
│   │   ├── java/com/hypex/toolbox/
│   │   │   ├── MainActivity.kt     # Main app entry
│   │   │   ├── spoofing/           # Spoofing logic
│   │   │   └── ui/                 # Compose UI screens
│   │   ├── res/                    # Android resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── toolbox-data/
│   ├── device-model.json           # 30+ device fingerprints
│   ├── pif-props.json              # Active Play Integrity fingerprint
│   └── app-props.json              # Per-app spoofing rules
├── framework-patches/
│   ├── SystemProperties.patch      # Framework patch for property spoofing
│   └── README.md                   # Patch instructions
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🔧 Getting Started

### Option 1: Generate from GitHub Actions (recommended)

1. Push this repo to GitHub
2. Go to **Actions** → **Generate Jetpack Compose Template**
3. Click **Run workflow**
4. Enter your app name and package name
5. The Action generates the full project and commits it

### Option 2: Clone and open locally

```bash
git clone https://github.com/YOUR_USERNAME/Hypex-UI-Toolbox.git
cd Hypex-UI-Toolbox
# Open in Android Studio
```

### Option 3: Generate manually

```bash
chmod +x .github/scripts/generate-project.sh
bash .github/scripts/generate-project.sh
```

---

## 🧪 Building the App

### Debug build:
```bash
./gradlew assembleDebug
```

### Release build:
```bash
./gradlew assembleRelease
```

### Generate signed APK with GitHub Actions:
1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Add your keystore secrets:
   - `KEYSTORE_BASE64` - base64 encoded keystore
   - `KEYSTORE_PASSWORD` - keystore password
   - `KEY_ALIAS` - key alias
   - `KEY_PASSWORD` - key password
3. Push a tag: `git tag v1.0.0 && git push origin v1.0.0`
4. Action builds, signs, and creates a Release

---

## 🧩 Framework Integration

To make the spoofing work natively in your Hypex-UI ROM:

1. Apply `framework-patches/SystemProperties.patch` to your ROM source
2. Ensure the app is installed as a **system app** with system UID
3. The app writes configs to `/data/system/hypex/pif.json`
4. Framework reads configs at runtime → spoofing works without any modules

---

## 📦 Requirements

- **ROM:** Hypex-UI (based on HyperOS 3 / Android 14-15)
- **Device:** Xiaomi device with unlocked bootloader
- **Optional:** TrickyStore for STRONG integrity

---

## 🛣️ Roadmap

- [x] Project scaffolding with GitHub Actions
- [ ] Device fingerprint database (30+ devices)
- [ ] Play Integrity fix / spoofing
- [ ] Per-app spoofing profiles
- [ ] TrickyStore keybox manager
- [ ] HyperOS debloater
- [ ] MSA / ad removal
- [ ] Game FPS unlocker
- [ ] Framework patches documentation
- [ ] Google Photos unlimited backup

---

## 🙏 Credits

- [Kaorios-Toolbox](https://github.com/Wuang26/Kaorios-Toolbox) - Reference implementation
- [Play Integrity Fix](https://github.com/chiteroman/PlayIntegrityFix) - Fingerprint spoofing
- [TrickyStore](https://github.com/5ec1cff/TrickyStore) - KeyStore attestation

---

## 📱 Join the Community

- Telegram: [t.me/HypexUI](https://t.me/HypexUI)

---

*Built with ❤️ for the Hypex-UI community*
