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


    const val room_runtime= "androidx.room:room-runtime:${Versions.room_version}"
    const val room_ktx= "androidx.room:room-ktx:${Versions.room_version}"

   const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"



    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_components}"
    const val navigation_runtime = "androidx.navigation:navigation-runtime:${Versions.nav_components}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_components}"
    const val navigation_dynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_components}"

}