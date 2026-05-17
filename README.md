# tv-app

Frontend-only Android TV IPTV GUI built with Kotlin + Jetpack Compose for TV.

Status: mock UI only. No backend, no real IPTV streams, no payment, no movie catalog.

## Features

- Splash screen with auto-navigation after 2 seconds
- Dual-mode frontend:
  - TV layout with left sidebar, big cards, and focus-first D-pad behavior
  - Phone layout with bottom navigation, featured hero section, and cleaner mobile cards
- Filtered live channel grid using fake data only
- D-pad friendly focus states with scale/highlight effects
- Player placeholder screen
- Settings placeholder screen
- MVVM-style frontend structure
- Media3/ExoPlayer dependency added for future playback work
- Android Studio Compose previews for TV and mobile variants

## Tech stack

- Kotlin
- Android Gradle Plugin
- Jetpack Compose
- Compose Navigation
- Compose for TV Material
- Media3 ExoPlayer

## Minimum requirements

- Linux
- Java 17
- Android SDK Platform 34 installed
- Android build-tools installed

This project does not require emulator testing right now.

## Build

Verified on Linux with:

```bash
./gradlew assembleDebug
```

Result:

```text
BUILD SUCCESSFUL
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Open in Android Studio

1. Start Android Studio
2. Open the `tv-app` folder
3. Let Gradle sync finish
4. Build with `assembleDebug` or the IDE build button
5. For design previews, open:
   - `app/src/main/java/com/hevar/tvapp/ui/PreviewGallery.kt`
   - `app/src/main/java/com/hevar/tvapp/ui/home/HomeScreen.kt`

## Project structure

```text
tv-app/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/hevar/tvapp/
│       │   ├── data/
│       │   ├── model/
│       │   ├── navigation/
│       │   ├── theme/
│       │   └── ui/
│       │       ├── home/
│       │       ├── player/
│       │       ├── settings/
│       │       └── splash/
│       └── res/
├── gradle/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

## Notes

- Fake repository only: `FakeChannelRepository`
- Real playback intentionally not implemented yet
- Local SDK paths are not committed
- `local.properties` stays local and is ignored by Git
- The project is ready for GitHub push with a clean Android `.gitignore`
