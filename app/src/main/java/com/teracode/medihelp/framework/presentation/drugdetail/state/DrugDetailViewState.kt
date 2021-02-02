package com.teracode.medihelp.framework.presentation.drugdetail.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
class DrugDetailViewState(

    var drugId: String? = null,
    var selectedDrug: Drug? = null,
    var layoutManagerState: Parcelable? = null,

    ) : ViewState, Parcelable {
}