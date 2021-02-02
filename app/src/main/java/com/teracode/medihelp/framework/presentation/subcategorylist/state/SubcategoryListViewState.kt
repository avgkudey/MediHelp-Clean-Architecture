package com.teracode.medihelp.framework.presentation.subcategorylist.state

import android.os.Parcelable
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.business.domain.state.ViewState
import com.teracode.medihelp.util.OrderEnum
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubcategoryListViewState(
    var subcategoryList: ArrayList<Subcategory>? = null,
    var category: Category? = null,
    var page: Int? = null,
    var isQueryExhausted: Boolean? = null,
    var filter: String? = null,
    var searchQuery: String? = null,
    var order: OrderEnum? = null,
    var layoutManagerState: Parcelable? = null,
    var numSubcategoriesInCache: Int? = null
) : Parcelable, ViewState {

}