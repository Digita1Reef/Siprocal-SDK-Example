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

## Client checklist

1. Use the SDK variant provided by Siprocal.
2. Add the configuration JSON delivered for your environment.
3. Validate required permissions and manifest entries.
4. Confirm the app starts successfully before enabling optional features.

## Recommendation

Treat this step as mandatory and keep it independently verifiable before continuing with Firebase or UI-specific integrations.
