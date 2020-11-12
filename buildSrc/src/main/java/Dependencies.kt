import org.gradle.api.JavaVersion

// For AndroidX libs versions see: https://developer.android.com/jetpack/androidx/versions
// For Ktx libs versions see: https://developer.android.com/kotlin/ktx#core-packages
// For Material lib version see: https://mvnrepository.com/artifact/com.google.android.material/material
// For Firebase libs versions see: https://firebase.google.com/support/release-notes/android

object Versions {
    const val androidMinSdk = 21
    const val androidCompileSdk = 28
    const val androidTargetSdk = androidCompileSdk

    val javaSourceCompatibility = JavaVersion.VERSION_1_8
    const val kotlinJvmTarget = "1.8"

    const val kotlin = "1.4.10"
    const val lifecycle = "2.2.0"
    const val paging = "2.1.2"
    const val okhttp = "4.9.0"
    const val retrofit = "2.9.0"
    const val room = "2.2.5"
    const val moshi = "1.11.0"
    const val glide = "4.11.0"
}

object Dependencies {
    // Kotlin
    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"

    // AndroidX - General
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val core = "androidx.core:core-ktx:1.3.2"
    const val activity = "androidx.activity:activity-ktx:1.1.0"
    const val fragment = "androidx.fragment:fragment-ktx:1.2.5"

    // AndroidX - UI
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0"

    // AndroidX - Lifecycle
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    // AndroidX - Paging
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    const val pagingRxJava = "androidx.paging:paging-rxjava2-ktx:${Versions.paging}"

    // UI
    const val material = "com.google.android.material:material:1.2.0"
    const val flexbox = "com.google.android:flexbox:2.0.1"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideOkhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    // Network
    const val okHttpClient = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofitClient = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitAdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    // Database
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomProcessor = "androidx.room:room-compiler:${Versions.room}"

    // JSON
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    // Rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.20"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"

    // DI
    const val koin = "org.koin:koin-androidx-viewmodel:2.1.6"

    // Firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:26.0.0"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

    // Tools
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.5"

    // Testing
    const val junit = "junit:junit:4.13.1"
    const val assertJ = "org.assertj:assertj-core:3.17.2"
    const val mockk = "io.mockk:mockk:1.10.2"
    const val lifecycleTesting = "androidx.arch.core:core-testing:2.1.0"
}
