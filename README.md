# 🎮 Game Booster Pro

A professional Android game booster app that optimizes your device for gaming — clears RAM, kills background processes, and launches games faster.

## Features

- ⚡ **One-tap Boost** — animated RAM optimizer with staged progress feedback
- 🎮 **Game Library** — manage and search your installed games
- 📊 **Dashboard** — track launches, favorites, health score, RAM & storage
- ★ **Favorites** — mark games for quick access
- 🚀 **Boost & Launch** — optimizes device then launches the game directly
- 💾 **Storage Monitor** — real-time free/used/total storage breakdown

## Screenshots

> Add screenshots here after running the app.

## Tech Stack

- **Language:** Kotlin
- **Min SDK:** 21
- **UI:** XML layouts + Material Components 3
- **Architecture:** Activity-based, SharedPreferences for persistence

## Setup

1. Clone the repo
   ```bash
   git clone https://github.com/swatiPanchal11/game-booster-pro.git
   ```
2. Open in **Android Studio**
3. Add to `app/build.gradle`:
   ```groovy
   implementation 'com.google.android.material:material:1.11.0'
   ```
4. Build & run on a device or emulator (API 21+)

## Project Structure

```
app/src/main/
├── java/com/xoun/gamebooster/
│   ├── activity/
│   │   ├── BoostActivity.kt
│   │   ├── DashboardActivity.kt
│   │   ├── GamesActivity.kt
│   │   ├── GamesDetailsActivity.kt
│   │   └── ManageGamesActivity.kt
│   ├── adapters/
│   ├── models/
│   └── utils/
└── res/
    ├── layout/
    ├── values/
    └── drawable/
```

## Contributing

Pull requests are welcome. For major changes, open an issue first.

## License

[MIT](LICENSE)
