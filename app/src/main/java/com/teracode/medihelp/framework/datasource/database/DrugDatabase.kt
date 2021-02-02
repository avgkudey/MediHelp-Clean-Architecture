package com.teracode.medihelp.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.SubcategoryCacheEntity
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuestionCacheEntity
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuizCacheEntity
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuestionDao
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuizDao

@Database(
    entities = [
        DrugCacheEntity::class,
        CategoryCacheEntity::class,
        SubcategoryCacheEntity::class,
        QuizCacheEntity::class,
        QuestionCacheEntity::class
    ],
    version = 3
)
abstract class DrugDatabase : RoomDatabase() {

    abstract fun drugDao(): DrugDao

    abstract fun categoryDao(): CategoryDao

    abstract fun subcategoryDao(): SubcategoryDao

    abstract fun quizDao(): QuizDao

    abstract fun questionDao(): QuestionDao


    companion object {
        val DATABASE_NAME: String = "drug_db"
    }
}