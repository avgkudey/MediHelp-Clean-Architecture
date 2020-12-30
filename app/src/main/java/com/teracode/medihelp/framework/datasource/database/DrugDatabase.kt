package com.teracode.medihelp.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.SubcategoryCacheEntity

@Database(
    entities = arrayOf(
        DrugCacheEntity::class,
        CategoryCacheEntity::class,
        SubcategoryCacheEntity::class
    ), version = 1
)
abstract class DrugDatabase : RoomDatabase() {

    abstract fun drugDao(): DrugDao

    abstract fun categoryDao(): CategoryDao

    abstract fun subcategoryDao(): SubcategoryDao


    companion object {
        val DATABASE_NAME: String = "drug_db"
    }
}