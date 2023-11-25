plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
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

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
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

                implementation("app.cash.sqldelight:runtime:2.0.0")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
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
                implementation("app.cash.sqldelight:android-driver:2.0.0")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.activity:activity-compose:1.8.0")
            }
        }
        val androidUnitTest by getting
        val iosMain by creating {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
            dependsOn(commonMain)
        }

    }
}

android {
    namespace = "com.iwanickimarcel.freat"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("ProductsDatabase") {
            packageName.set("com.iwanickimarcel.freat.products_database")
            srcDirs.setFrom("src/commonMain/sqldelight_products")
        }
        create("ProductsSearchHistoryDatabase") {
            packageName.set("com.iwanickimarcel.freat.products_search_history_database")
            srcDirs.setFrom("src/commonMain/sqldelight_products_search_history")
        }
        create("RecipesDatabase") {
            packageName.set("com.iwanickimarcel.freat.recipes_database")
            srcDirs.setFrom("src/commonMain/sqldelight_recipes")
        }
        create("RecipesSearchHistoryDatabase") {
            packageName.set("com.iwanickimarcel.freat.recipes_search_history_database")
            srcDirs.setFrom("src/commonMain/sqldelight_recipes_search_history")
        }
    }
}

dependencies {
    implementation("androidx.core:core:1.12.0")
    implementation("com.google.mlkit:text-recognition:16.0.0")
    commonMainImplementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc05")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
}
