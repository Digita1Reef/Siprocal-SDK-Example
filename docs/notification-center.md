# Notification Center

This guide explains the local notification center example included in the sample app.

## Main files to review

- `app/src/main/java/com/siprocal/sdkexample/ui/view/NotificationActivity.kt`
- `app/src/main/java/com/siprocal/sdkexample/ui/view/NotificationAdapter.kt`
- `app/src/main/java/com/siprocal/sdkexample/ui/viewmodel/NotificationViewModel.kt`
- `app/src/main/java/com/siprocal/sdkexample/data/local/`
- `app/src/main/res/layout/activity_notification.xml`
- `app/src/main/res/layout/item_notification.xml`

## What this stage covers

- Persistence of notification records
- Basic list UI for notification history
- ViewModel and repository wiring for notification state

## Client checklist

1. Decide whether your app needs an in-app notification center.
2. Reuse the storage and UI layers only if they fit your product requirements.
3. Treat this sample as reference code, not as a mandatory SDK dependency.

## Recommendation

This should remain documented as an optional integration module. It is useful as a pattern, but many clients will have their own persistence and presentation layers.
