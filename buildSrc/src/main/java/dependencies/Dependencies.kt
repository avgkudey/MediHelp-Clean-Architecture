package dependencies

object Dependencies {
    const val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    const val firebase_firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    const val firebase_firestore =
        "com.google.firebase:firebase-firestore-ktx"
    const val firebase_auth = "com.google.firebase:firebase-auth-ktx"
    const val firebase_analytics =
        "com.google.firebase:firebase-analytics-ktx"
    const val firebase_crashlytics =
        "com.google.firebase:firebase-crashlytics-ktx"


    const val hilt_android =
        "com.google.dagger:hilt-android:${Versions.hilt_android}"

    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
    const val kotlin_coroutines_play_services =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"


}