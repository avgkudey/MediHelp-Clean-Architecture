package com.teracode.medihelp.business.data.network.implementation

import com.teracode.medihelp.business.data.network.abstraction.CategoryNetworkDataSource
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.framework.datasource.network.abstraction.CategoryFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryNetworkDataSourceImpl
@Inject
constructor(
    private val firestoreService: CategoryFirestoreService
) : CategoryNetworkDataSource {
    override suspend fun searchCategory(category: Category) =
        firestoreService.searchCategory(category)


    override suspend fun getAllCategories() = firestoreService.getAllCategories()
}