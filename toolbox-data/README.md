# Toolbox Data Files

These JSON files are the configuration database for the Hypex-UI Toolbox app.

- `device-model.json` — Device spoofing profiles (fingerprints, models)
- `pif-props.json` — Active Play Integrity Fix fingerprint
- `app-props.json` — Per-app spoofing rules

The app reads these files and your patched framework reads the config at runtime.
