package com.teracode.medihelp.framework.presentation.common

import android.text.Editable
import java.util.*


fun Float.centimeterToInch() = this / 2.54.toFloat()
fun Float.inchToCentimeter() = this * 2.54.toFloat()
fun Float.kilogramToPound() = this * 2.20464.toFloat()
fun Float.poundToKilogram() = this / 2.20464.toFloat()