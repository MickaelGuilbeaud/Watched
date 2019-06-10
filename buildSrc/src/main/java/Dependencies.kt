// For AndroidX libs versions see: https://developer.android.com/jetpack/androidx/versions
// For Ktx libs versions see: https://developer.android.com/kotlin/ktx#core-packages
// For Firebase libs versions see: https://firebase.google.com/support/release-notes/android

object Versions {
    const val kotlin = "1.3.31"
    const val kotlinCoroutines = "1.3.0-M1"
    const val lifecycle = "2.1.0-beta01"
    const val okhttp = "3.14.1"
    const val retrofit = "2.5.0"
    const val glide = "4.9.0"
}

object Dependencies {
    // Kotlin
    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // AndroidX - General
    const val appcompat = "androidx.appcompat:appcompat:1.1.0-beta01"
    const val coreKtx = "androidx.core:core-ktx:1.2.0-alpha01"

    // AndroidX - UI
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta1"
    const val material = "com.google.android.material:material:1.1.0-alpha06"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-alpha06"

    // AndroidX - Lifecycle
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"   // ViewModel and LiveData
    const val livedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"  // Extensions functions for LiveData
    const val viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"    // Extensions functions for ViewModel

    // Network
    const val okHttpClient = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofitClient = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitAdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    // Rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.8"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    // UI
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideOkhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    // Tools
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.9.9@aar"
}