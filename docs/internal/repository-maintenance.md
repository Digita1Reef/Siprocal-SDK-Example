# Repository Maintenance

This document defines the branch model and maintenance rules for the example repository.

## Branch model

- `main`: complete integration example
- `step/01-basic`
- `step/02-splash`
- `step/03-firebase`
- `step/04-notification-center`
- `step/05-sensitive-data`

Each `step/*` branch must be cumulative and based on the previous step.

## Working rules

- Keep `main` buildable.
- Keep each `step/*` branch buildable.
- Apply shared fixes in the earliest relevant `step/*` branch and propagate forward when needed.
- Keep the public step sequence aligned with the documentation in `README.md` and `docs/`.

## Branch hygiene

- Use `step/*` for the maintained progressive example path.
- Keep task branches such as `feature/*`, `chore/*`, or ticket-specific names out of the documented branch model.
- Remove or archive obsolete branches once their replacements are validated.

## Release notes

- Tags are optional.
- If tags are used, they should point to stable checkpoints in the maintained branch sequence.

## Documentation rules

- Public documentation should describe setup and integration behavior.
- Repository maintenance guidance should stay in internal-facing documents.
- When branch strategy changes, update this file and any public references accordingly.
