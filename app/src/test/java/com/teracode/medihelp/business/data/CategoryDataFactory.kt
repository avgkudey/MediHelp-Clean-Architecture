package com.teracode.medihelp.business.data

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.teracode.medihelp.business.domain.model.Category

class CategoryDataFactory(
    private val testClassLoader: ClassLoader
) {

    fun getCategoriesFromFile(fileName: String): String {
        return testClassLoader.getResource(fileName).readText()
    }

    fun produceEmptyListOfCategories(): List<Category> {
        return ArrayList()
    }


    fun produceHashMapOfCategories(categoryList: List<Category>): HashMap<String, Category>{
        val map = HashMap<String, Category>()
        for(category in categoryList){
            map.put(category.id, category)
        }
        return map
    }


    fun produceListOfCategories(): List<Category>{
        val drugs: List<Category> = Gson()
            .fromJson(
                getCategoriesFromFile("categories.json"),
                object: TypeToken<List<Category>>() {}.type
            )
        return drugs
    }
}