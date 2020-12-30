package com.teracode.medihelp.business.data.network.abstraction

import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug

interface CategoryNetworkDataSource {
    suspend fun searchCategory(category: Category): Category?

    suspend fun getAllCategories(): List<Category>

}