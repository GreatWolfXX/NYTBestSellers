import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.gwolf.nytbestsellers"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gwolf.nytbestsellers"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.8.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val nytBestSellersApi = properties.getProperty("NYT_BEST_SELLERS_API") ?: ""
        val firebaseAuthClientId = properties.getProperty("FIREBASE_AUTH_CLIENT_ID") ?: ""

        buildConfigField("String", "NYT_BEST_SELLERS_API", "\"$nytBestSellersApi\"")
        buildConfigField("String", "FIREBASE_AUTH_CLIENT_ID", "\"$firebaseAuthClientId\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.android.compiler)

    // Kotlinx serialization
    implementation(libs.serialization.json)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Credentials Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    // Google ID
    implementation(libs.googleid)

    // Ktor
    implementation(libs.ktor.client)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.logging)

    // Coil
    implementation(libs.coil)

    // Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // SplashScreen
    implementation(libs.splashscreen)

    // Timber
    implementation(libs.timber)
}