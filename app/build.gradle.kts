plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin") // the above plugin are really to get dagger/hilt to work for dependency injection
    id("androidx.navigation.safeargs")
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

        testInstrumentationRunner = "com.github.sacsar.hellodiceroller.HiltTestRunner"
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
    val fragment_version = "1.4.1"
    val lifecycle_version = "2.4.+"
    val nav_version = "2.4.0"
    val hilt_version = "2.41"
    val auto_value_version = "1.9"

    api("com.google.auto.value:auto-value-annotations:$auto_value_version")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.fragment:fragment:$fragment_version")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("com.google.dagger:hilt-android:$hilt_version")
    annotationProcessor("com.google.dagger:hilt-android-compiler:$hilt_version")
    annotationProcessor("com.google.auto.value:auto-value:$auto_value_version")

    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxjava
    implementation("io.reactivex.rxjava3:rxjava:3.1.4")
    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxandroid -- note that rxandroid doesn't release as
    // often as rxjava, so the versions shouldn't align
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.fragment:fragment-testing:$fragment_version")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:$hilt_version")
}

spotless {
    java {
        importOrder()

        removeUnusedImports()
        googleJavaFormat()

        // need to explicitly help it find android sources
        target("src/*/java/**/*.java")
    }

    format("xml") {
        target("src/**/*.xml") // must specify target
        eclipseWtp(com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep.XML)
        ratchetFrom("origin/main")
    }
}