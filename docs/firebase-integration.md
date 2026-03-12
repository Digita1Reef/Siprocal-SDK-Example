# Firebase Integration

This guide covers the Firebase Cloud Messaging integration used in the sample.

## Main files to review

- `app/build.gradle.kts`
- `build.gradle.kts`
- `app/google-services.json`
- `app/src/main/java/com/siprocal/sdkexample/ui/service/YourFirebaseMessagingService.kt`
- `app/src/main/AndroidManifest.xml`

## What this stage covers

- Firebase dependency setup
- Google services plugin usage
- FCM service declaration
- Push message handling handoff

## Client checklist

1. Add your own Firebase project configuration.
2. Register the messaging service correctly in the manifest.
3. Test token generation and push delivery in a non-production environment.
4. Confirm the integration contract between Firebase events and Siprocal SDK.

## Recommendation

Keep Firebase-specific setup clearly separated from the base SDK setup in documentation. Clients that do not use Firebase should be able to ignore this section without affecting the rest of the integration.
