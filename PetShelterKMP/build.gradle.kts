plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidKmpLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.mokkery) apply false
    alias(libs.plugins.kover)
}

dependencies {
    kover(project(":composeApp"))
    kover(project(":androidApp"))
}

kover {
    reports {
        filters {
            excludes {
                packages(
                    "*.di",
                    "*.generated.*",
                    "com.petshelter.core.platform",
                    "com.petshelter.designsystem.icons",
                )
                classes(
                    "*_Factory",
                    "*_HiltModules*",
                    "*ComposableSingletons*",
                    "*BuildConfig",
                    "*NoOp*",
                    "*Fake*",
                    "*Stub*",
                    "*Mock*",
                    "*InMemory*",
                    "*Recording*",
                    "com.petshelter.MainKt",
                    "com.petshelter.MainActivity",
                    "*PlatformEmail*",
                )
                annotatedBy(
                    "androidx.compose.ui.tooling.preview.Preview",
                    "androidx.compose.runtime.Composable",
                )
            }
        }
    }
}

tasks.register("allChecks") {
    group = "verification"
    description = "Runs ktlint, detekt, and all platform tests"
    dependsOn(
        ":composeApp:ktlintCheck",
        ":androidApp:ktlintCheck",
        ":composeApp:detekt",
        ":composeApp:jvmTest",
        ":composeApp:jsBrowserTest",
        ":composeApp:wasmJsBrowserTest",
        ":composeApp:iosSimulatorArm64Test",
        ":androidApp:testDebugUnitTest",
    )
}

tasks.register<Copy>("installGitHooks") {
    group = "git"
    description = "Installs git hooks from the hooks directory into .git/hooks"
    from("hooks")
    into(".git/hooks")
    filePermissions {
        user {
            read = true
            write = true
            execute = true
        }
        group {
            read = true
            execute = true
        }
        other {
            read = true
            execute = true
        }
    }
}

tasks.named("prepareKotlinBuildScriptModel") {
    dependsOn("installGitHooks")
}

subprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(rootProject.files("config/detekt/detekt.yml"))
        parallel = true
        source.setFrom(files("src"))
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude {
            it.file.absolutePath.contains("/build/")
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.5.0")
        android.set(true)
        outputToConsole.set(true)
        filter {
            exclude { element -> element.file.path.contains("/generated/") }
        }
    }
}
