package com.teracode.medihelp.framework.presentation.drugcategory.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
class DrugCategoryViewState(
    var categoryList: ArrayList<Category>? = null,
    var layoutManagerState: Parcelable? = null,
    var numSubcategoriesInCache: Int? = null
) : Parcelable, ViewState {
}