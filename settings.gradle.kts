pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Freat"
include(":androidApp")
include(":shared")
include(":shared:presentation:recipes")
include(":shared:presentation:products")
include(":shared:presentation:add_ingredient")
include(":shared:presentation:add_product")
include(":shared:presentation:add_recipe")
include(":shared:presentation:add_step")
include(":shared:presentation:home")
include(":shared:presentation:products_search")
include(":shared:presentation:recipes_search")
include(":shared:presentation:scan_bill")
include(":shared:presentation:settings")
include(":shared:domain:add_product")
include(":shared:domain:add_recipe")
include(":shared:domain:products")
include(":shared:data:products")
include(":shared:data:recipes")
include(":shared:data:products_search")
include(":shared:data:recipes_search")
include(":shared:domain:add_step")
include(":shared:domain:recipes")
include(":shared:domain:products_search")
include(":shared:data:core")
include(":shared:presentation:core")
