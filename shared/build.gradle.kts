plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }
    
    // We can add iosArm64(), iosX64(), iosSimulatorArm64() later if needed
    // iosX64()
    // iosArm64() 
    // iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.koin.core)
            implementation(libs.room.runtime)
        }
    }
}

android {
    namespace = "com.klar.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
