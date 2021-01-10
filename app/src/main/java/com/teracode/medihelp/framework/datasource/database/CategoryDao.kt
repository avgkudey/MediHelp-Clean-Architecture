package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.CategoryCacheEntity
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity


@Dao
interface CategoryDao {

    /**
     * Inserts a single CategoryCacheEntity model in to the cache [Long]
     */
    @Insert
    suspend fun insertCategory(category: CategoryCacheEntity): Long

    /**
     * Inserts multiple CategoryCacheEntity models in to the cache (onConflict - ignore)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<CategoryCacheEntity>): LongArray


    /**
     * Returns CategoryCacheEntity model of given ID  [CategoryCacheEntity]?
     */
    @Query("SELECT * FROM categories WHERE id= :id LIMIT 1")
    suspend fun searchCategoryById(id: String): CategoryCacheEntity?


    /**
     * Returns CategoryCacheEntity model of given Title  [CategoryCacheEntity]?
     */
    @Query("SELECT * FROM categories WHERE title= :title LIMIT 1")
    suspend fun searchCategoryByTitle(title: String): CategoryCacheEntity?

    /**
     * Deletes category record in the cache of given ID [Int]
     */
    @Query("DELETE FROM categories WHERE id= :primaryKey")
    suspend fun deleteCategory(primaryKey: String): Int

    /**
     * Deletes multiple category records in the cache of given IDS [Int]
     */
    @Query("DELETE FROM categories WHERE id IN (:ids)")
    suspend fun deleteCategories(ids: List<String>): Int


    /**
     * Deletes all category records in the cache
     */
    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    /**
     * Updates category record in the cache [Int]
     */
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


    /***
     * Returns Number of Categories in the cache
     */
    @Query("SELECT COUNT(*) FROM categories")
    fun getNumCategories(): Int


    /**
     * Returns all category records in cache [List<CategoryCacheEntity>]
     */
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryCacheEntity>



}