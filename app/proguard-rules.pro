# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the SDK tools.

-keepattributes Signature
-keepattributes *Annotation*

# Keep Hypex-UI Toolbox data models
-keep class com.hypex.toolbox.data.** { *; }
