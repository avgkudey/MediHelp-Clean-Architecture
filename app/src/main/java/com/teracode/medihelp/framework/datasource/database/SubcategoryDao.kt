package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.SubcategoryCacheEntity


@Dao
interface SubcategoryDao {

    @Insert
    suspend fun insertSubcategory(subcategory: SubcategoryCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubcategories(subcategories: List<SubcategoryCacheEntity>): LongArray

    @Query("SELECT * FROM subcategories WHERE id= :id LIMIT 1")
    suspend fun searchSubcategoryById(id: String): SubcategoryCacheEntity?

    @Query("DELETE FROM subcategories WHERE id= :primaryKey")
    suspend fun deleteSubcategory(primaryKey: String): Int

    @Query("DELETE FROM subcategories WHERE id IN (:ids)")
    suspend fun deleteSubcategories(ids: List<String>): Int

    @Query("SELECT * FROM subcategories")
    suspend fun getAllSubcategories(): List<SubcategoryCacheEntity>


    @Query(
        """
        UPDATE subcategories
        SET
            title= :title,
            image= :image,
            categoryId= :categoryId,
            url= :url,
            description= :description
        WHERE id = :primaryKey
    """
    )
    suspend fun updateSubcategory(
        primaryKey: String,
        title: String,
        categoryId: String,
        image: String?,
        url: String?,
        description: String?
    ): Int

    @Query("SELECT COUNT(*) FROM subcategories")
    fun getNumSubcategories(): Int


    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<SubcategoryCacheEntity> {
        return ArrayList()
    }
}