package com.teracode.medihelp.business.data.network.implementation

import com.teracode.medihelp.business.data.network.abstraction.SubcategoryNetworkDataSource
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.datasource.network.abstraction.SubcategoryFirestoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubcategoryNetworkDataSourceImpl
@Inject
constructor(
    private val firestoreService: SubcategoryFirestoreService

) : SubcategoryNetworkDataSource {
    override suspend fun searchSubcategory(subcategory: Subcategory) =
        firestoreService.searchSubcategory(subcategory)

    override suspend fun getAllSubcategories() = firestoreService.getAllSubcategories()
}