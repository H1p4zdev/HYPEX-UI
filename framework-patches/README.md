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
