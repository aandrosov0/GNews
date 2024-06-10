plugins {
    kotlin("android") version "2.0.0"
    id("com.android.application") version "8.4.1"
    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
}

android {
    namespace = "aandrosov.gnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "aandrosov.gnews"

        minSdk = 29
        targetSdk = 34

        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("io.coil-kt:coil:2.6.0")
    implementation("androidx.fragment:fragment-ktx:1.7.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.12.0")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
}