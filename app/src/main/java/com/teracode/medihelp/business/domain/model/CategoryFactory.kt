package com.teracode.medihelp.business.domain.model

import java.util.*
import javax.inject.Singleton

@Singleton
class CategoryFactory {


    fun createSingleCategory(
        id: String? = null,
        title: String? = null,
        image: String? = null,
        url: String? = null,
        description: String? = null
    ): Category {
        return Category(
            id = id ?: UUID.randomUUID().toString(),
            title = title ?: UUID.randomUUID().toString(),
            image = image ?: "",
            url = url ?: "",
            description = description ?: UUID.randomUUID().toString(),
        )
    }


    fun createCategoryList(numCategory: Int): List<Category> {
        val list: ArrayList<Category> = ArrayList()

        for (i in 0 until numCategory) {
            list.add(
                createSingleCategory()
            )
        }

        return list
    }
}