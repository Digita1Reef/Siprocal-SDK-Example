# Getting Started

This project is intended to be consumed from `main`, which contains the complete example integration.

## Prerequisites

- Android Studio with a supported Gradle and Android SDK setup
- A Siprocal SDK package and configuration provided for your client account
- Firebase project configuration if push notifications are required

## Setup flow

1. Open the project in Android Studio.
2. Add the Siprocal configuration file to `app/src/main/assets/siprocal-config.json`.
3. Confirm the SDK dependency and variant in `app/build.gradle.kts`.
4. Add `google-services.json` if Firebase is part of your setup.
5. Review the manifest entries in `app/src/main/AndroidManifest.xml`.
6. Build and run the project.

## How to read this sample

- Use `main` if you need the full implementation.
- Use the guides in `docs/` to isolate each integration concern.
- Use the `step/*` branches if you want to inspect the integration progressively.
- Do not rely on historical feature branches as the primary onboarding path.

## Suggested onboarding order

1. `step/01-basic`
2. `step/02-splash`
3. `step/03-firebase`
4. `step/04-notification-center`
5. `step/05-sensitive-data`
