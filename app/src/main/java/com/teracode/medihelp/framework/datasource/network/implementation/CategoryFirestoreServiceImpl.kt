package com.teracode.medihelp.framework.datasource.network.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.domain.model.Category
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.network.abstraction.CategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.mappers.CategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.model.CategoryNetworkEntity
import com.teracode.medihelp.util.cLog
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryFirestoreServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val networkMapper: CategoryNetworkMapper
) : CategoryFirestoreService {
    override suspend fun searchCategory(category: Category): Category? {

        return firestore.collection(CATEGORY_COLLECTION).document(category.id)
            .get()
            .addOnFailureListener {
                cLog("${this::class.java} searchCategory ${it.message}")
            }
            .await()
            .toObject(CategoryNetworkEntity::class.java)?.let {
                networkMapper.mapFromEntity(it)
            }
    }

    override suspend fun getAllCategories(): List<Category> {
        return networkMapper.entityListToCategoryList(
            entities = firestore.collection(CATEGORY_COLLECTION).get()
                .addOnFailureListener {
                    cLog("${this::class.java} getAllCategories ${it.message}")
                }

                .await()
                .toObjects(CategoryNetworkEntity::class.java)
        )
    }


    companion object {
        const val CATEGORY_COLLECTION = "categories"
    }
}