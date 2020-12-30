package com.teracode.medihelp.business.data

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.teracode.medihelp.business.domain.model.Drug

class DrugDataFactory(
    private val testClassLoader: ClassLoader
) {

    fun getDrugsFromFile(fileName: String): String {
        return testClassLoader.getResource(fileName).readText()
    }

    fun produceEmptyListOfDrugs(): List<Drug> {
        return ArrayList()
    }


    fun produceHashMapOfDrugs(drugList: List<Drug>): HashMap<String, Drug>{
        val map = HashMap<String, Drug>()
        for(note in drugList){
            map.put(note.id, note)
        }
        return map
    }


    fun produceListOfDrugs(): List<Drug>{
        val drugs: List<Drug> = Gson()
            .fromJson(
                getDrugsFromFile("drug_list.json"),
                object: TypeToken<List<Drug>>() {}.type
            )
        return drugs
    }
}