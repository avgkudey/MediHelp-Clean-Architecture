package com.teracode.medihelp.business.domain.model

import java.util.*
import javax.inject.Singleton

@Singleton
class SubcategoryFactory {


    fun createSingleSubcategory(
        id: String? = null,
        title: String? = null,
        categoryId: String? = null,
        image: String? = null,
        url: String? = null,
        description: String? = null
    ): Subcategory {
        return Subcategory(
            id = id ?: UUID.randomUUID().toString(),
            title = title ?: UUID.randomUUID().toString(),
            categoryId = categoryId ?: UUID.randomUUID().toString(),
            image = image ?: "",
            url = url ?: "",
            description = description ?: UUID.randomUUID().toString(),
        )
    }


    fun createSubcategoryList(numCategory: Int): List<Subcategory> {
        val list: ArrayList<Subcategory> = ArrayList()

        for (i in 0 until numCategory) {
            list.add(
                createSingleSubcategory()
            )
        }

        return list
    }
}