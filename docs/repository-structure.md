# Repository Structure

This document describes the recommended repository model for a client-facing SDK example.

## Recommended branch model

- `main`: stable, full integration example
- `step/*`: customer-facing progressive integration path
- Internal development branches: private to the team, not part of the customer learning path

## Progressive step branches

Use a linear sequence:

1. `step/01-basic`
2. `step/02-splash`
3. `step/03-firebase`
4. `step/04-notification-center`
5. `step/05-sensitive-data`

## Why this is better than customer-facing feature branches

- Clients do not need to understand repository history to use the sample.
- Documentation becomes the primary onboarding surface.
- The full sample stays stable and easy to clone.
- Internal branch naming stays decoupled from the public integration story.
- The model reflects the real scenario: same SDK, same version, different stages of the integration example.

## About tags

Tags are optional. If you use them, they should point to stable milestones, but the primary customer-facing learning path should remain the `step/*` branches above.

## Maintenance rules

- Keep `main` always buildable.
- Keep each `step/*` branch buildable and cumulative.
- Update all guides when the SDK version changes.
- Avoid publishing old task branches such as `feature/*`, `chore/*`, or ticket-specific names as part of the external documentation.
- Prefer sample placeholders and setup instructions over shipping client-specific credentials or config files.
