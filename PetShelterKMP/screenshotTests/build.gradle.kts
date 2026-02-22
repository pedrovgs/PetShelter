plugins {
    id("com.android.library")
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.petshelter.screenshots"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.all {
            // Disable the HTML report to work around Paparazzi reporter incompatibility
            // with Gradle 8.14+. Test results are still available in XML format.
            it.reports.html.required
                .set(false)
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

// Make Compose Multiplatform resources available to Paparazzi tests.
// The resources are assembled for JVM but need to be on the Android test classpath too.
val composeResourcesDir =
    project(":composeApp").layout.buildDirectory.dir(
        "generated/compose/resourceGenerator/assembledResources/jvmMain",
    )

android.sourceSets.getByName("test") {
    resources.srcDir(composeResourcesDir)
}

tasks.matching { it.name.contains("UnitTestJavaRes") }.configureEach {
    dependsOn(":composeApp:assembleJvmMainResources")
}

tasks.withType<Test>().configureEach {
    dependsOn(":composeApp:assembleJvmMainResources")
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.androidx.activity.compose)

    implementation(libs.richeditor.compose)

    testImplementation(libs.paparazzi)
    testImplementation(libs.testParameterInjector)
    testImplementation(libs.junit)
    testImplementation(platform(libs.koin.bom))
    testImplementation(libs.koin.core)
    testImplementation(libs.koin.test)
}
