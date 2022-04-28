plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin") // the above plugin are really to get dagger/hilt to work for dependency injection
    id("com.diffplug.spotless") version "6.5.1"
}

android {
    compileSdk = 32

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.github.sacsar.hellodiceroller"
        minSdk = 30
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    val fragment_version = "1.4.1"
    val lifecycle_version = "2.4.+"
    val nav_version = "2.4.0"

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.fragment:fragment:$fragment_version")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("com.google.dagger:hilt-android:2.41")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.41")

    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxjava
    implementation("io.reactivex.rxjava3:rxjava:3.1.4")
    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxandroid -- note that rxandroid doesn't release as
    // often as rxjava, so the versions shouldn't align
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")


    // https://mvnrepository.com/artifact/com.google.android.material/material
    runtimeOnly("com.google.android.material:material:1.5.0")


    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

spotless {
    java {
        importOrder()

        removeUnusedImports()
        googleJavaFormat()

        // need to explicitly help it find android sources
        target("src/*/java/**/*.java")
    }
}