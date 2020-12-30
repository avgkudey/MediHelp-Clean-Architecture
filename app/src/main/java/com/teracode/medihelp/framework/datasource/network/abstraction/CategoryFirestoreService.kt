package com.teracode.medihelp.framework.datasource.network.abstraction

import com.teracode.medihelp.business.domain.model.Category

interface CategoryFirestoreService {
    suspend fun searchCategory(category: Category): Category?

    suspend fun getAllCategories(): List<Category>

}