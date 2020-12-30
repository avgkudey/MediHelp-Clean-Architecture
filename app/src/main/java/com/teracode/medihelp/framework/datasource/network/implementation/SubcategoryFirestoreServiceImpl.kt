package com.teracode.medihelp.framework.datasource.network.implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.domain.model.Subcategory
import com.teracode.medihelp.framework.datasource.network.abstraction.SubcategoryFirestoreService
import com.teracode.medihelp.framework.datasource.network.mappers.SubcategoryNetworkMapper
import com.teracode.medihelp.framework.datasource.network.model.SubcategoryNetworkEntity
import com.teracode.medihelp.util.cLog
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubcategoryFirestoreServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val networkMapper: SubcategoryNetworkMapper
) : SubcategoryFirestoreService {
    override suspend fun searchSubcategory(subcategory: Subcategory): Subcategory? {

        return firestore.collection(SUBCATEGORY_COLLECTION).document(subcategory.id)
            .get()
            .addOnFailureListener {
                cLog("${this::class.java} searchCategory ${it.message}")
            }
            .await()
            .toObject(SubcategoryNetworkEntity::class.java)?.let {
                networkMapper.mapFromEntity(it)
            }
    }

    override suspend fun getAllSubcategories(): List<Subcategory> {
        return networkMapper.entityListToSubcategoryList(
            entities = firestore.collection(SUBCATEGORY_COLLECTION).get()
                .addOnFailureListener {
                    cLog("${this::class.java} getAllCategories ${it.message}")
                }

                .await()
                .toObjects(SubcategoryNetworkEntity::class.java)
        )
    }


    companion object {
        const val SUBCATEGORY_COLLECTION = "subcategories"
    }
}