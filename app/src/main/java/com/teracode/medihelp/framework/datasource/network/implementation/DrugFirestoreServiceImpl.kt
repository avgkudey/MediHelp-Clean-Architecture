package com.teracode.medihelp.framework.datasource.network.implementation

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.teracode.medihelp.business.domain.model.Drug
import com.teracode.medihelp.framework.datasource.network.abstraction.DrugFirestoreService
import com.teracode.medihelp.framework.datasource.network.mappers.DrugNetworkMapper
import com.teracode.medihelp.framework.datasource.network.model.DrugNetworkEntity
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugFirestoreServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val networkMapper: DrugNetworkMapper
)

    : DrugFirestoreService {
    override suspend fun searchDrug(drug: Drug): Drug? {
        return firestore.collection(DRUGS_COLLECTION)
            .document(drug.id)
            .get()
            .addOnFailureListener {

            }
            .await().toObject(DrugNetworkEntity::class.java)?.let {
                networkMapper.mapFromEntity(it)
            }
    }

    override suspend fun getAllDrugs(): List<Drug> {


        var exception: Exception? = null
        return firestore.collection(DRUGS_COLLECTION)
            .get()
            .addOnFailureListener {
                exception=it
                Log.d("syncNetworkDru", "addOnFailureListener: ${it}")
            }.addOnCompleteListener { task->
                Log.d("syncNetworkDru", "addOnCompleteListener: ${task.exception}")

            }
            .await()
            .toObjects(DrugNetworkEntity::class.java).let {


                Log.d("syncNetworkDru", "getAllDrugs toObjects: ${it}")

                networkMapper.entityListToDrugList(it)
            }


//        return networkMapper.entityListToDrugList(entities = firestore.collection(DRUGS_COLLECTION)
//            .get()
//            .addOnFailureListener {
//
//            }
//            .await()
//            .toObjects(DrugNetworkEntity::class.java)
//        )
    }


    companion object {
        const val DRUGS_COLLECTION = "drugs"
    }
}