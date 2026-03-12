# Siprocal SDK Android Example

This repository is the reference Android sample for teams integrating Siprocal SDK.

`main` contains the full integration currently supported in this example project. Use it as the default starting point if you want a working end-to-end reference.

## Recommended entry points

- Use `main` if you want the complete integration example.
- Use the `step/*` branches if you want to review the integration stage by stage.
- Do not use historical internal branches as the customer onboarding path.

## What this sample includes

- Basic SDK initialization
- Splash support example
- Firebase / FCM integration
- Notification center example
- Sensitive data flow example

## Recommended way to use this repository

1. Start from `main`.
2. Follow the setup steps in this README.
3. Use the guides in [`docs/`](./docs) to understand or adapt each integration area.
4. Use the `step/*` branches if you want to review the implementation progressively.

This is simpler for clients than switching across multiple branches to reconstruct the final implementation.

## Progressive integration branches

The repository now includes a linear branch sequence for teams that want to review the integration step by step:

- `step/01-basic`
- `step/02-splash`
- `step/03-firebase`
- `step/04-notification-center`
- `step/05-sensitive-data`

Each branch is based on the previous one.

## Quick start

1. Clone the repository.
2. Open the project in Android Studio.
3. Replace [`app/src/main/assets/siprocal-config.json`](./app/src/main/assets/siprocal-config.json) with the configuration file provided by Siprocal.
4. Replace the SDK variant in [`app/build.gradle.kts`](./app/build.gradle.kts) with the one assigned to your project.
5. Add your Firebase config file at [`app/google-services.json`](./app/google-services.json) if your integration uses FCM.
6. Ensure `jitpack.io` is available in [`settings.gradle.kts`](./settings.gradle.kts) when required by your SDK variant.
7. Build and run the app.

## Integration guides

- [Getting started](./docs/getting-started.md)
- [Basic integration](./docs/basic-integration.md)
- [Splash support](./docs/splash-support.md)
- [Firebase integration](./docs/firebase-integration.md)
- [Notification center](./docs/notification-center.md)
- [Sensitive data](./docs/sensitive-data.md)

## Feature map

| Feature | Status in `main` | Guide |
| --- | --- | --- |
| SDK initialization | Included | [Basic integration](./docs/basic-integration.md) |
| Splash support | Included | [Splash support](./docs/splash-support.md) |
| Firebase / FCM | Included | [Firebase integration](./docs/firebase-integration.md) |
| Notification center | Included | [Notification center](./docs/notification-center.md) |
| Sensitive data flow | Included | [Sensitive data](./docs/sensitive-data.md) |

## Required project files

- `app/src/main/assets/siprocal-config.json`
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/siprocal/sdkexample/MainApplication.kt`

## Client-facing repository conventions

- `main` is the stable, full example.
- Step-by-step guidance lives in `docs/`.
- Use the linear `step/*` branches to represent integration checkpoints of the same SDK.

## Support notes

If your project uses a different SDK variant, environment, or notification provider setup, adapt the sample using the corresponding Siprocal delivery package and use these guides as the implementation reference.
