package dependencies

object AnnotationProcessing {
    const val hilt_android_compiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"

    const val hilt_compiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_lifecycle_view_model}"

    const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"

    const val glide_compiler="com.github.bumptech.glide:compiler:${Versions.glide}"
}