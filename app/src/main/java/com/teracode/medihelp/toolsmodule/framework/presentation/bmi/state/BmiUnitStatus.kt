package com.teracode.medihelp.toolsmodule.framework.presentation.bmi.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface BmiUnitStatus : Parcelable {
    val heightSuffix: String
    val weightSuffix: String
}

@Parcelize
class Metric(
    override val heightSuffix: String = "cm",
    override val weightSuffix: String = "kg",
) : BmiUnitStatus, Parcelable

@Parcelize
class Imperial(
    override val heightSuffix: String = "In",
    override val weightSuffix: String = "lbs",
) : BmiUnitStatus, Parcelable
