// --- REEMPLAZA TODO EL CONTENIDO DEL ARCHIVO CON ESTO ---

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.simplemanager"
    // Usamos compileSdk 34 (versión estable) para evitar problemas de compatibilidad
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.simplemanager"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Aseguramos una versión de Kotlin Compiler compatible con el código
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    // --- DEPENDENCIAS BÁSICAS ---
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")

    // Compose BOM para manejo de versiones
    val composeBom = platform("androidx.compose:compose-bom:2023.03.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // --- DEPENDENCIAS CRÍTICAS (Solucionan los errores Unresolved Reference) ---

    // 1. DataStore Preferences (SOLUCIONA todos los errores de 'datastore' y 'preferences')
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // 2. Navigation Compose (Ya tenías esta, pero la mantenemos con la versión estable)
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // 3. ViewModel Compose (Soluciona errores de Factory)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // 4. Icons Extended (SOLUCIONA 'Logout', 'Mail')
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // 5. Compose Animation
    implementation("androidx.compose.animation:animation:1.4.3")


    // Dependencias de Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}