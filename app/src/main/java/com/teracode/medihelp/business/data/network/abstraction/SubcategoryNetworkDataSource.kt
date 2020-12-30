package com.teracode.medihelp.business.data.network.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.business.domain.model.Subcategory

interface SubcategoryNetworkDataSource {
    suspend fun searchSubcategory(subcategory: Subcategory): Subcategory?

    suspend fun getAllSubcategories(): List<Subcategory>

}