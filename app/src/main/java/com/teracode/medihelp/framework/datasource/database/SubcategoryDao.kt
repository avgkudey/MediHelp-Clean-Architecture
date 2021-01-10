package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.SubcategoryCacheEntity
import com.teracode.medihelp.util.OrderEnum

const val SUBCATEGORY_PAGINATION_PAGE_SIZE = 20

@Dao
interface SubcategoryDao {


    /**
     * Inserts a single SubcategoryCacheEntity model in to the cache [Long]
     */
    @Insert
    suspend fun insertSubcategory(subcategory: SubcategoryCacheEntity): Long

    /**
     * Inserts multiple SubcategoryCacheEntity models in to the cache (onConflict - ignore)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubcategories(subcategories: List<SubcategoryCacheEntity>): LongArray


    /**
     * Returns SubcategoryCacheEntity model of given ID  [SubcategoryCacheEntity]?
     */
    @Query("SELECT * FROM subcategories WHERE id= :id LIMIT 1")
    suspend fun searchSubcategoryById(id: String): SubcategoryCacheEntity?

    /**
     * Deletes subcategory record in the cache of given ID [Int]
     */
    @Query("DELETE FROM subcategories WHERE id= :primaryKey")
    suspend fun deleteSubcategory(primaryKey: String): Int

    /**
     * Deletes multiple subcategory records in the cache of given IDS [Int]
     */
    @Query("DELETE FROM subcategories WHERE id IN (:ids)")
    suspend fun deleteSubcategories(ids: List<String>): Int

    /**
     * Deletes all subcategory records in the cache
     */
    @Query("DELETE FROM subcategories")
    suspend fun deleteAllSubcategories()

    /**
     * Updates subcategory record in the cache [Int]
     */
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


    /**
     * Returns all subcategory records in cache [List<SubcategoryCacheEntity>]
     */
    @Query("SELECT * FROM subcategories")
    suspend fun getAllSubcategories(): List<SubcategoryCacheEntity>

    /**
     *  Returns all subcategory records in the cache Oder by title ASC with pagination [List<SubcategoryCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM subcategories
        where title LIKE '%' || :query
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchAllOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<SubcategoryCacheEntity>


    /**
     *  Returns all subcategory records in the cache Oder by title DESC [List<SubcategoryCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM subcategories
        where title LIKE '%' || :query
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchAllOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<SubcategoryCacheEntity>


    /**
     *  Returns subcategory records where categoryId in the cache Oder by title ASC [List<SubcategoryCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM subcategories
        where (title LIKE '%' || :query) AND categoryId= :categoryId
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereCategoryOrderByTitleASC(
        query: String,
        categoryId: String,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<SubcategoryCacheEntity>


    /**
     *  Returns subcategory records where categoryId in the cache Oder by title DESC [List<SubcategoryCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM subcategories
        where (title LIKE '%' || :query) AND categoryId= :categoryId
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereCategoryOrderByTitleDESC(
        query: String,
        categoryId: String,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<SubcategoryCacheEntity>


    suspend fun returnOrderedQuery(
        query: String,
        categoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int = SUBCATEGORY_PAGINATION_PAGE_SIZE
    ): List<SubcategoryCacheEntity> {

        return when (filterAndOrder) {
            OrderEnum.ORDER_ASC -> {

                when {
                    categoryId != null -> {
                        searchWhereCategoryOrderByTitleASC(
                            query = query,
                            categoryId = categoryId,
                            page = page,
                            pageSize = pageSize
                        )
                    }
                    else -> {
                        searchAllOrderByTitleASC(
                            query = query,
                            page = page,
                            pageSize = pageSize
                        )
                    }
                }

            }
            OrderEnum.ORDER_DESC -> {

                when {
                    categoryId != null -> {
                        searchWhereCategoryOrderByTitleDESC(
                            query = query,
                            categoryId = categoryId,
                            page = page,
                            pageSize = pageSize
                        )
                    }
                    else -> {
                        searchAllOrderByTitleDESC(
                            query = query,
                            page = page,
                            pageSize = pageSize
                        )
                    }
                }

            }
        }

    }

    /***
     * Returns all number of subcategory records [Int]
     */
    @Query("SELECT COUNT(*) FROM subcategories")
    fun getNumAllSubcategories(): Int


    /***
     * Returns number of subcategory records in the cache of given category [Int]
     */
    @Query("SELECT COUNT(*) FROM subcategories  WHERE categoryId = :categoryId")
    fun getNumInCategory(categoryId: String): Int

    /***
     * Returns filtered Number of Drug records in the cache [Int]
     */
    suspend fun getNumSubcategories(
        categoryId: String?,
    ): Int {
        return when {
            categoryId != null -> {
                getNumInCategory(categoryId)
            }

            else -> getNumAllSubcategories()
        }
    }
}