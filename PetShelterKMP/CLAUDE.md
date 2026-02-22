# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform (KMP) project using Compose Multiplatform for PetShelter. Targets 6 platforms: Android, iOS, Desktop (JVM), JavaScript, WebAssembly, and Web. Three main modules: `composeApp` (shared KMP library with platform-specific source sets), `androidApp` (Android application module that depends on `composeApp`), and `screenshotTests` (Paparazzi-based screenshot tests).

## Build Commands

All commands use the Gradle wrapper (`./gradlew`) from the project root.

### Android
```bash
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:testDebugUnitTest
./gradlew :androidApp:connectedDebugAndroidTest   # instrumented tests (requires emulator/device)
```

### iOS
```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64   # build framework
./gradlew :composeApp:iosSimulatorArm64Test                 # run tests
```
The iOS app itself is built via Xcode from `iosApp/iosApp.xcodeproj`.

### Desktop (JVM)
```bash
./gradlew :composeApp:run             # run the app
./gradlew :composeApp:jvmTest         # run tests
```

### Web (JS / Wasm)
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun       # JS dev server
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # Wasm dev server
./gradlew :composeApp:jsBrowserTest
./gradlew :composeApp:wasmJsBrowserTest
```

### All Platforms
```bash
./gradlew allTests    # run all tests and generate report
./gradlew check       # run all verification tasks
./gradlew build       # build all targets
```

### Screenshot Tests (Paparazzi)
```bash
./gradlew :screenshotTests:recordPaparazziDebug    # record new baselines
./gradlew :screenshotTests:verifyPaparazziDebug    # verify against baselines
```
Screenshot tests live in `screenshotTests/` module. Uses [Paparazzi](https://github.com/cashapp/paparazzi) for JVM-based snapshot testing (no emulator needed). Tests are parameterized by `ThemeVariant` (Light/Dark) and `DevicePreset` (phone/tablet/desktop sizes). Baseline images are stored in `screenshotTests/src/test/snapshots/images/`.

**When modifying UI components, always re-record screenshots:** `./gradlew :screenshotTests:recordPaparazziDebug`

### Code Coverage (Kover)
```bash
./gradlew koverHtmlReport    # HTML report → build/reports/kover/html/index.html
./gradlew koverXmlReport     # XML report (CI integration)
./gradlew koverLog           # print summary to console
./gradlew koverVerify        # check coverage thresholds
```
Coverage is collected for JVM and Android targets. Reports exclude generated code, DI modules, and `@Composable`/`@Preview` functions.

### Linting
```bash
./gradlew ktlintCheck              # check all modules
./gradlew ktlintFormat             # auto-fix all modules
./gradlew :composeApp:detekt       # run detekt static analysis
```
Both ktlint and detekt are applied to all modules via the root `build.gradle.kts`. Detekt config is in `config/detekt/detekt.yml`.

## Architecture

### Module Layout
```
androidApp/              # Android application module (com.android.application)
├── src/main/
│   ├── kotlin/com/petshelter/MainActivity.kt
│   ├── AndroidManifest.xml
│   └── res/             # Launcher icons, strings, drawables

composeApp/              # Shared KMP library module (com.android.library)
├── src/
│   ├── commonMain/      # Shared Compose UI and business logic
│   ├── commonTest/      # Shared tests
│   ├── androidMain/     # Android platform actuals
│   ├── iosMain/         # iOS UIViewController bridge, platform actuals
│   ├── jvmMain/         # Desktop entry point, platform actuals
│   ├── jsMain/          # JS platform actuals
│   ├── wasmJsMain/      # Wasm platform actuals
│   └── webMain/         # Shared web entry point (JS + Wasm)

screenshotTests/         # Paparazzi screenshot test module (com.android.library)
├── src/test/
│   ├── kotlin/com/petshelter/screenshots/
│   │   ├── components/  # Component-level screenshot tests
│   │   └── pages/       # Page-level screenshot tests
│   └── snapshots/images/  # Baseline screenshot PNGs
```

### Platform Abstraction
Uses Kotlin `expect`/`actual` for platform-specific code. Platform declarations live in `commonMain` (e.g., `Platform.kt`) with actual implementations in each platform source set (e.g., `Platform.android.kt`, `Platform.ios.kt`).

### iOS Integration
The iOS app (`iosApp/`) is a SwiftUI wrapper that embeds Compose UI via `UIViewControllerRepresentable`. The KMP module produces a static framework (`ComposeApp.framework`) consumed by the Xcode project.

### Key Versions
- Kotlin: 2.3.0
- Compose Multiplatform: 1.10.0
- Gradle: 8.14.3
- Android: minSdk 24, targetSdk 36, compileSdk 36
- iOS deployment target: 18.2

### Version Catalog
All dependency versions are managed in `gradle/libs.versions.toml`.

## Conventions

- Package: `com.petshelter`
- Code style: `kotlin.code.style=official` (standard Kotlin conventions)
- Platform-specific files use suffix pattern: `Platform.android.kt`, `Platform.ios.kt`, etc.
- Configuration cache and build cache are enabled

### Android Module Split
The Android application lives in `androidApp/` (uses `com.android.application` plugin) and depends on `:composeApp` (uses `com.android.library` plugin). This separation is required because combining `org.jetbrains.kotlin.multiplatform` with `com.android.application` in the same module is deprecated and will break with AGP 9.0.0+.

## Mandatory: Platform Compatibility

**Every new feature MUST compile and run on all 4 platforms: Android, iOS, Desktop (JVM), and Web (JS + WasmJS).** This is non-negotiable.

- All shared code goes in `commonMain`. Platform-specific code uses `expect`/`actual` declarations.
- When adding an `expect` declaration, you MUST provide `actual` implementations in ALL platform source sets: `androidMain`, `iosMain`, `jvmMain`, `jsMain`, and `wasmJsMain`.
- If a platform API is unavailable, provide a no-op or graceful fallback (see `DebugInfo.wasmJs.kt` for examples).
- Before considering a PR complete, verify it compiles on all targets:
  ```bash
  ./gradlew :composeApp:jvmJar :androidApp:assembleDebug :composeApp:jsBrowserProductionWebpack :composeApp:wasmJsBrowserProductionWebpack :composeApp:linkDebugFrameworkIosSimulatorArm64
  ```
- Never add a dependency that only works on a single platform unless it is behind an `actual` implementation with alternatives for all other platforms.

## Mandatory: Design System

All UI code MUST use the project design system. **Never use hardcoded colors, text styles, spacing, or radii outside the design system.**

### Color System
- Colors are defined in `composeApp/src/commonMain/kotlin/com/petshelter/designsystem/Color.kt` as a `PetShelterColorScheme` data class with `LightPetShelterColors` and `DarkPetShelterColors` instances.
- Access colors via `PetShelterTheme.colors.PropertyName` (e.g., `PetShelterTheme.colors.Primary`). **Never use `Color(0xFF...)` directly in UI code.**
- To add a new color: add a property to `PetShelterColorScheme`, provide values in both `LightPetShelterColors` and `DarkPetShelterColors`, then use via `PetShelterTheme.colors`.
- The only exception is decorative/content-specific colors (e.g., AI gradient borders, template preview fills) that are intentionally independent of the theme.

### Typography, Spacing, and Layout
- Typography: use `PetShelterTypography` object (e.g., `PetShelterTypography.headingLarge`, `PetShelterTypography.bodyMedium`).
- Spacing: use `Spacing` object (e.g., `Spacing.xs`, `Spacing.md`, `Spacing.lg`).
- Corner radii: use `Radii` object (e.g., `Radii.sm`, `Radii.lg`).
- Layout constants: use `Layout` object for standard dimensions (e.g., `Layout.SidebarWidth`, `Layout.CardWidth`).
- Animation durations: use `AnimationDuration` object (e.g., `AnimationDuration.short`, `AnimationDuration.medium`).

### Theme
- `PetShelterTheme` composable in `Theme.kt` wraps `MaterialTheme` and provides both Material and custom color schemes via `CompositionLocal`.
- Dark/light mode is automatic via `isSystemInDarkTheme()`. The theme is reactive to system changes.
- All composables MUST be wrapped in `PetShelterTheme` for correct theming.

### Mandatory Previews
- Every `@Composable` function that represents a UI component or page MUST have at least one `@Preview` function.
- Previews should cover key states (empty, loading, error, populated).
- For pages with ViewModels, preview the inner content composable that accepts state as parameters, not the ViewModel-connected wrapper.
- Import: `import androidx.compose.ui.tooling.preview.Preview`.

## Architecture Patterns

### Clean Architecture + MVVM
The project follows Clean Architecture with MVVM for the presentation layer. All code lives under `com.petshelter`:

```
core/                    # Shared foundation (pure Kotlin + platform actuals)
├── model/               # Domain entities (Document, Annotation, etc.)
├── repository/          # Repository interfaces
├── data/                # Repository implementations
├── usecase/             # Use cases (single-responsibility, invoke operator)
├── service/             # Platform services (PdfRenderer, ThumbnailRenderer, etc.)
├── storage/             # File storage abstraction
├── db/                  # SQLDelight database driver factory
└── platform/            # Platform detection (expect/actual)

feature/                 # Feature modules, each with ui/ and optional usecase/
├── home/ui/             # Home page (QuickActions, RecentDocuments, etc.)
├── documents/ui/        # All documents page
├── favourites/ui/       # Favourites page
├── trash/ui/            # Trash page
├── importing/           # PDF import (ui/ + usecase/)
├── note/ui/             # Note editor
├── pdf/ui/              # PDF viewer
└── pdfmarkup/ui/        # PDF annotation editor

components/              # Shared UI components (AppCard, AppButton, Modal, NavigationSidebar, DocumentCard, etc.)
navigation/              # Type-safe navigation (Route.kt, AppNavigation.kt)
designsystem/            # Theme, colors, typography, spacing, radii, icons
di/                      # Koin DI modules (AppModule.kt + platform modules)
util/                    # Shared utilities (ImageDecoder, etc.)
debug/                   # Debug info page
```

### State Management
- ViewModels expose state via `StateFlow<UiState>` where `UiState` is a data class.
- Composables collect state with `val uiState by viewModel.uiState.collectAsState()`.
- State classes use `data class` with sensible defaults (e.g., `isLoading = true`, `items = emptyList()`).

### Dependency Injection (Koin)
- DI is configured in `di/AppModule.kt` using Koin DSL.
- Use `single { }` for singletons, `factory { }` for new instances per injection, `viewModel { }` for ViewModels.
- Platform-specific DI uses `expect val platformModule: Module` with `actual` implementations per platform.
- In composables, inject ViewModels with `koinViewModel<MyViewModel>()`.
- Android requires `androidContext()` passed via `App(platformKoinConfig)`.

### Navigation
- Type-safe navigation using `@Serializable` route definitions in a sealed interface hierarchy.
- Routes defined in `navigation/Route.kt`.
- Navigation graph built in `navigation/AppNavigation.kt` using `NavHost` and `composable<Route>` DSL.
- Navigate with `navController.navigate(Route.Detail(id = "..."))`.

### Repository Pattern
- Interfaces in `core/repository/` define the contract.
- Implementations in `core/data/` use platform data sources.
- Repositories are injected into use cases and ViewModels via Koin.

### Component Conventions
- All composable components accept `modifier: Modifier = Modifier` as a parameter.
- Use `interactionSource` for hover/press states in interactive components.
- Use `animate*AsState` for smooth visual transitions.
- Extract private helper composables to keep public composables readable.
