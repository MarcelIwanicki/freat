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
        val androidUnitTest by getting

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

dependencies {
    implementation(project(mapOf("path" to ":shared:data:core")))
    implementation(project(mapOf("path" to ":shared:data:products")))
    implementation(project(mapOf("path" to ":shared:data:products_search")))
    implementation(project(mapOf("path" to ":shared:data:recipes")))
    implementation(project(mapOf("path" to ":shared:data:recipes_search")))
    implementation(project(mapOf("path" to ":shared:domain:products")))
    implementation(project(mapOf("path" to ":shared:domain:products_search")))
    implementation(project(mapOf("path" to ":shared:domain:add_product")))
    implementation(project(mapOf("path" to ":shared:domain:recipes")))
    implementation(project(mapOf("path" to ":shared:domain:recipes_search")))
    implementation(project(mapOf("path" to ":shared:domain:add_recipe")))
    implementation(project(mapOf("path" to ":shared:domain:add_step")))
    implementation(project(mapOf("path" to ":shared:domain:scan_bill")))
    implementation(project(mapOf("path" to ":shared:presentation:core")))
    implementation(project(mapOf("path" to ":shared:presentation:add_ingredient")))
    implementation(project(mapOf("path" to ":shared:presentation:products")))
    implementation(project(mapOf("path" to ":shared:presentation:products_search")))
    implementation(project(mapOf("path" to ":shared:presentation:recipes")))
    implementation(project(mapOf("path" to ":shared:presentation:recipes_search")))
    implementation(project(mapOf("path" to ":shared:presentation:add_product")))
    implementation(project(mapOf("path" to ":shared:presentation:add_step")))
    implementation(project(mapOf("path" to ":shared:presentation:add_recipe")))
    implementation(project(mapOf("path" to ":shared:presentation:scan_bill")))

    implementation("androidx.core:core:1.12.0")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.insert-koin:koin-android:3.2.0")
    commonMainImplementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc05")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
}
