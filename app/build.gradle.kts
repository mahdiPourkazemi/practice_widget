plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    // اختیاری: برای serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)

}

android {
    namespace = "com.example.learningwidget"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.learningwidget"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // For Glance support
    implementation(libs.androidx.glance)
    // For AppWidgets support
    implementation(libs.androidx.glance.appwidget)
    // For Wear-Tiles support
    implementation(libs.androidx.glance.wear.tiles)
//work manager
    implementation(libs.androidx.work.runtime.ktx)
    // datastore preferences
    implementation(libs.androidx.datastore.preferences)
    //dataStore proto
    implementation(libs.androidx.datastore)

    // Room
    implementation(libs.room.runtime)
//    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx) // برای Coroutines پشتیبانی
    ksp(libs.room.compiler.ksp)

    // Compose Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    // Lifecycle و ViewModel پایه
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Material Icons Extended
    implementation(libs.androidx.compose.material.icons.extended)
    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // For ViewModel support with Hilt
    implementation(libs.androidx.hilt.navigation.compose)


    // وابستگی‌های اصلی Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    // وابستگی‌های اختیاری
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)  // برای ViewModel scoping
    implementation(libs.kotlinx.serialization.json)  // اگر serialization نیاز دارید

    // برای adaptive UI (اختیاری؛ snapshot)
    implementation(libs.androidx.material3.adaptive.navigation3)

}