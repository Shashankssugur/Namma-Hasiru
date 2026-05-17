# Namma-Hasiru (Natural Resources)

Beginner-friendly Android app (Kotlin + Jetpack Compose) to track tree plantation efforts:

- Add a plant with **photo + GPS location**
- Store data locally using **Room**
- View **home summary**, **survival score**, and **recent activity**
- Update plant status: **Survived / Dead** (+ growth photo)
- “Tree Map”: list of plants with **Open in Google Maps**
- **WorkManager** reminder after ~90 days: “Time to update your plant growth status!”
- Species Guide: basic suggestions based on soil type

## Open & Run

1. Open this folder in **Android Studio**.
2. Let Gradle sync finish.
3. Run on a device/emulator (Android 7.0+).

## Permissions used

- Location: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- Camera: `CAMERA`
- Notifications (Android 13+): `POST_NOTIFICATIONS`

## Notes

- Photos are stored as `content://` URIs (camera uses an app cache `FileProvider` URI).
- The reminder worker runs once per day and checks if any plant is **>= 90 days old** and still **Pending**.

