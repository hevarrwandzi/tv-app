# tv-app

[![Android Debug Build](https://github.com/hevarrwandzi/tv-app/actions/workflows/android-debug.yml/badge.svg)](https://github.com/hevarrwandzi/tv-app/actions/workflows/android-debug.yml)

Frontend-only IPTV UI for Android TV and Android phone, built with Kotlin + Jetpack Compose.

Status:
- mock UI only
- no backend
- no real IPTV streams
- no payment
- no movie catalog

## Highlights

- Dual-mode frontend
  - TV mode: large focus-first layout, left sidebar, D-pad friendly cards
  - Mobile mode: bottom navigation, featured hero section, cleaner cards
- Splash screen with auto-navigation after 2 seconds
- Category filtering with fake/mock channels only
- Player placeholder screen ready for future Media3 integration
- Settings placeholder screen
- Android TV banner + custom app branding
- Compose previews for TV and mobile screens
- MVVM-style structure with fake repository and simple navigation

## Tech stack

- Kotlin
- Android Gradle Plugin
- Jetpack Compose
- Compose Navigation
- Compose for TV Material
- Compose Material 3
- Media3 ExoPlayer

## Build

Verified on Linux with:

```bash
./gradlew assembleDebug
```

Expected result:

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
5. For previews, open:
   - `app/src/main/java/com/hevar/tvapp/ui/PreviewGallery.kt`
   - `app/src/main/java/com/hevar/tvapp/ui/home/HomeScreen.kt`

## GitHub Actions

This repo includes a CI workflow:
- `.github/workflows/android-debug.yml`

It does the following on every push to `main`, pull request, or manual trigger:
- sets up JDK 17
- runs `./gradlew assembleDebug`
- uploads `app-debug.apk` as a workflow artifact

## Project structure

```text
tv-app/
├── .github/
│   └── workflows/
│       └── android-debug.yml
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
│           └── drawable/
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
- Emulator testing is not required right now because your current setup has ADB/KVM issues
- The project is ready for GitHub collaboration and CI
