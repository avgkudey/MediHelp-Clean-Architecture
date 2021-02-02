package com.teracode.medihelp.framework.presentation.common

import android.text.Editable
import java.util.*


fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }

fun String.spliceToLines(delimiter: String, bullet: String = "", sort: Boolean = false): String {
    val updatedBullet = "$bullet "



    return when {
        this.contains(delimiter) -> {
            var sb: String = ""
            var arr = this.split(delimiter)
            if (sort) {
                arr = arr.sorted()
            }

            for (str in arr) {
                sb += (updatedBullet + str + "\n")
            }

            sb

        }
        this.isNotEmpty() -> {
            updatedBullet + this
        }
        else -> {
            this
        }
    }

}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)








