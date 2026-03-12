# Sensitive Data

This guide documents the sample flow related to sensitive data handling.

## Main files to review

- `app/src/main/java/com/siprocal/sdkexample/ui/view/DialogSensitiveData.kt`
- `app/src/main/java/com/siprocal/sdkexample/ui/view/MainActivity.kt`
- `app/src/main/java/com/siprocal/sdkexample/datastore/`
- `app/src/main/res/layout/popup_permission.xml`

## What this stage covers

- User-facing permission or consent flow
- Local storage of user decisions
- UI controls for enabling or reviewing the behavior

## Client checklist

1. Validate the exact compliance requirements for your product and market.
2. Adapt the copy, consent timing, and storage rules to your legal requirements.
3. Avoid copying this flow blindly into production without policy review.

## Recommendation

Mark this area clearly as compliance-sensitive. Clients should understand that this sample demonstrates one possible integration path, not a universal legal implementation.
