package com.teracode.medihelp.framework.presentation.druglist.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
class DrugListViewState(
    var drugList: ArrayList<Drug>? = null,
    var searchQuery: String? = null,
    var page: Int? = null,
    var isQueryExhausted: Boolean? = null,
    var filter: String? = null,
    var order: String? = null,
    var layoutManagerState: Parcelable? = null,
    var numDrugsInCache: Int? = null
) : Parcelable, ViewState {

}