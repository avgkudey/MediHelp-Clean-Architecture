package com.teracode.medihelp.framework.presentation.common

import java.util.*


fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.capitalize(Locale.ROOT) }