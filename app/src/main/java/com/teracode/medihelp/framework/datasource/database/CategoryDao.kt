package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity


@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: CategoryCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<CategoryCacheEntity>): LongArray

    @Query("SELECT * FROM categories WHERE id= :id LIMIT 1")
    suspend fun searchCategoryById(id: String): CategoryCacheEntity?

    @Query("DELETE FROM categories WHERE id= :primaryKey")
    suspend fun deleteCategory(primaryKey: String): Int

    @Query("DELETE FROM categories WHERE id IN (:ids)")
    suspend fun deleteCategories(ids: List<String>): Int

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryCacheEntity>


    @Query(
        """
        UPDATE categories
        SET
            title= :title,
            image= :image,
            url= :url,
            description= :description
        WHERE id = :primaryKey
    """
    )
    suspend fun updateCategory(
        primaryKey: String,
        title: String,
        image: String?,
        url: String?,
        description: String?
    ): Int

    @Query("SELECT COUNT(*) FROM categories")
    fun getNumCategories(): Int


    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<CategoryCacheEntity> {
        return ArrayList()
    }
}