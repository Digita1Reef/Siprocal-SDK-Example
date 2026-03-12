# Splash Support

This guide explains the splash-related integration used by the sample.

## Main files to review

- `app/src/main/java/com/siprocal/sdkexample/ui/view/SplashActivity.kt`
- `app/src/main/res/layout/activity_splash.xml`
- `app/src/main/AndroidManifest.xml`

## What this stage covers

- Startup routing through a splash screen
- Manifest launcher configuration
- Transition from splash flow to the main app screen

## Client checklist

1. Confirm whether your app already has a splash strategy.
2. Reuse this approach only if it matches your startup flow.
3. Keep SDK initialization independent from splash-only UI concerns when possible.

## Recommendation

Document splash support as optional behavior. Many client apps already have their own startup orchestration and should only copy the parts that are required.
