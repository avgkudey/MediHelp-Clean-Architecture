package com.teracode.medihelp.toolsmodule.framework.presentation.bmi.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.state.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
class BmiViewState(
    var bmiFields: BmiFields? = null,
    var unitStatus: BmiUnitStatus = Imperial(),
) : ViewState, Parcelable {
}


@Parcelize
data class BmiFields(
    var height: String? = null,
    var weight: String? = null,
) : Parcelable