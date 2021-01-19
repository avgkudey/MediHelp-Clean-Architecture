package com.teracode.medihelp.framework.presentation.common

import java.util.*


fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }

fun String.spliceToLines(delimiter: String, bullet: String = "", sort: Boolean = false): String {
    val updatedBullet = "$bullet "



    return if (this.contains(delimiter)) {
        var sb: String = ""
        var arr = this.split(delimiter)
        if (sort) {
            arr = arr.sorted()
        }

        for (str in arr) {
            sb += (updatedBullet + str + "\n")
        }

        sb

    } else if (this.isNotEmpty()) {
        updatedBullet + this
    } else {
        this
    }

}