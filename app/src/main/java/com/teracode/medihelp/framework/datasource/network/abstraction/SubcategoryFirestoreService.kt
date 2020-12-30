package com.teracode.medihelp.framework.datasource.network.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Subcategory

interface SubcategoryFirestoreService {
    suspend fun searchSubcategory(subcategory: Subcategory): Subcategory?

    suspend fun getAllSubcategories(): List<Subcategory>

}