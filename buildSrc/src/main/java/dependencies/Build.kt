package dependencies

object Build {
    const val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt="com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android}"
    const val google_services="com.google.gms:google-services:${Versions.google_services}"
    const val firebase_crashlytics_gradle="com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics_gradle}"

    const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_components}"
}