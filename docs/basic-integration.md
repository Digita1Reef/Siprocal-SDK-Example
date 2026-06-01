# Basic Integration

This is the minimum setup required to bootstrap Siprocal SDK in the sample app.

## Main files to review

- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/siprocal/sdkexample/MainApplication.kt`
- `app/src/main/assets/siprocal-config.json`

## What this stage covers

- SDK dependency setup
- SDK initialization in the application layer
- Base manifest configuration
- Loading the Siprocal configuration asset

## SDK dependency

The sample is pinned to Siprocal SDK `5.10.1` in `gradle/libs.versions.toml`. `app/build.gradle.kts` keeps `<variant>` as a placeholder so clients must use the Siprocal module assigned to their account. Provide that module with `siprocalSdkVariant`, for example `-PsiprocalSdkVariant=<assigned-module>`.

## Client checklist

1. Confirm the SDK variant provided by Siprocal.
2. Add the configuration JSON delivered for your environment.
3. Validate required permissions and manifest entries.
4. Confirm the app starts successfully before enabling optional features.

## Recommendation

Treat this step as mandatory and keep it independently verifiable before continuing with Firebase or UI-specific integrations.
