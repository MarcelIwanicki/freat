plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.activity:activity-compose:1.8.0")
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:runner:1.5.2")
                implementation(compose.desktop.uiTestJUnit4)
            }
        }
        val androidUnitTest by getting
    }
}

android {
    namespace = "com.iwanickimarcel.products"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(mapOf("path" to ":shared:domain:products")))
    implementation(project(mapOf("path" to ":shared:presentation:core")))
    implementation(project(mapOf("path" to ":shared:presentation:add_product")))
    commonTestImplementation(project(mapOf("path" to ":shared:test")))

    implementation("androidx.core:core:1.12.0")
    commonMainImplementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc05")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")

    commonTestImplementation("org.assertj:assertj-core:3.22.0")
    commonTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    commonTestImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestDebugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
}