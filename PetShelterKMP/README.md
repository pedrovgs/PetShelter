# PetShelter KMP

A **Kotlin Multiplatform** pet shelter application built with **Compose Multiplatform**, delivering a unified UI and shared business logic across six platforms from a single codebase.

---

## Platforms

| Platform | Target | Status |
|----------|--------|--------|
| Android | Phone & Tablet | Supported |
| iOS | iPhone & iPad | Supported |
| Desktop | macOS, Windows, Linux (JVM) | Supported |
| JavaScript | Modern & legacy browsers | Supported |
| WebAssembly | Modern browsers (Wasm) | Supported |
| Web (shared) | JS + Wasm shared entry point | Supported |

---

## Project Structure

```
PetShelterKMP/
├── androidApp/              Android application module (com.android.application)
├── composeApp/              Shared KMP library (all platforms)
│   └── src/
│       ├── commonMain/         Shared Compose UI and business logic
│       ├── androidMain/        Android platform actuals
│       ├── iosMain/            iOS UIViewController bridge
│       ├── jvmMain/            Desktop entry point
│       ├── jsMain/             JavaScript platform actuals
│       ├── wasmJsMain/         WebAssembly platform actuals
│       └── webMain/            Shared web entry point (JS + Wasm)
└── iosApp/                  iOS application (Xcode project)
```

---

## Getting Started

### Prerequisites

- **JDK 17** or later
- **IntelliJ IDEA** (2024.1+) or **Android Studio** (Ladybug+) with the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)
- **Xcode 16+** (for iOS development on macOS)
- **Android SDK** with API 36 installed (via SDK Manager)

### Build & Run

```bash
./gradlew :composeApp:run              # Desktop (JVM)
./gradlew :androidApp:assembleDebug    # Android
./gradlew :composeApp:jsBrowserDevelopmentRun       # JS web
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # Wasm web
```

For iOS, open `iosApp/iosApp.xcodeproj` in Xcode.

---

## Architecture

The project follows **Clean Architecture + MVVM**. All shared code lives under the `com.petshelter` package.

### Key Patterns

- **State Management** — ViewModels expose `StateFlow<UiState>` collected by Composables via `collectAsState()`
- **Dependency Injection** — [Koin](https://insert-koin.io/) with `single {}`, `factory {}`, and `viewModel {}` scopes
- **Navigation** — Type-safe `@Serializable` route definitions with `NavHost` composable DSL
- **Platform Abstraction** — Kotlin `expect`/`actual` for platform-specific code
- **Design System** — `PetShelterTheme`, `PetShelterTypography`, `Spacing`, `Radii`, `Layout`

---

## Key Versions

| Dependency | Version |
|-----------|---------|
| Kotlin | 2.3.0 |
| Compose Multiplatform | 1.10.0 |
| Gradle | 8.14.3 |
| Android compileSdk | 36 |
| Android minSdk | 24 |
| iOS deployment target | 18.2 |
| Koin | 4.1.1 |

---

<p align="center">
  Built with Kotlin Multiplatform and Compose Multiplatform
</p>
