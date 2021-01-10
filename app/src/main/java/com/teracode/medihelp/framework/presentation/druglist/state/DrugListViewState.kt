package com.teracode.medihelp.framework.presentation.druglist.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.ViewState
import com.teracode.medihelp.util.OrderEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrugListViewState(
    var drugList: ArrayList<Drug>? = null,
    var category: Category? = null,
    var subcategory: Subcategory? = null,
    var searchQuery: String? = null,
    var page: Int? = null,
    var isQueryExhausted: Boolean? = null,
    var filter: String? = null,
    var order: OrderEnum? = null,
    var layoutManagerState: Parcelable? = null,
    var numDrugsInCache: Int? = null
) : Parcelable, ViewState {

}