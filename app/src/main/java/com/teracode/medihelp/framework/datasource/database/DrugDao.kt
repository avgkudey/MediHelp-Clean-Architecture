package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity
import com.teracode.medihelp.util.OrderEnum

const val DRUG_ORDER_DESC: String = "-"
const val DRUG_FILTER_DATE_CREATED = "created_at"


const val DRUG_PAGINATION_PAGE_SIZE = 20

@Dao
interface DrugDao {


    /**
     * Inserts a single DrugCacheEntity model in to the cache [Long]
     */
    @Insert
    suspend fun insertDrug(drug: DrugCacheEntity): Long

    /**
     * Inserts multiple DrugCacheEntity models in to the cache (onConflict - ignore)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrugs(drugs: List<DrugCacheEntity>): LongArray


    /**
     * Returns DrugCacheEntity model of given ID  [DrugCacheEntity]?
     */
    @Query("SELECT * FROM drugs WHERE id= :id LIMIT 1")
    suspend fun searchDrugById(id: String): DrugCacheEntity?

    /**
     * Deletes drug record in the cache of given ID [Int]
     */
    @Query("DELETE FROM drugs WHERE id= :primaryKey")
    suspend fun deleteDrug(primaryKey: String): Int

    /**
     * Deletes multiple drug records in the cache of given IDS [Int]
     */
    @Query("DELETE FROM drugs WHERE id IN (:ids)")
    suspend fun deleteDrugs(ids: List<String>): Int

    /**
     * Deletes all drug records in the cache
     */
    @Query("DELETE FROM drugs")
    suspend fun deleteAllDrugs()

    /**
     * Updates drug record in the cache [Int]
     */
    @Query(
        """
        UPDATE drugs
        SET
            title= :title,
            trade_name= :trade_name,
            pharmacological_name= :pharmacological_name,
            actions= :action,
            antidote= :antidote,
            cautions= :cautions,
            contraindication= :contraindication,
            dosages= :dosages,
            indications= :indications,
            maximum_dose= :maximum_dose,
            nursing_implications= :nursing_implications,
            notes= :notes,
            preparation= :preparation,
            side_effects= :side_effects,
            video= :video,
            category_id= :category_id,
            subcategory_id= :subcategory_id
        WHERE id = :primaryKey
    """
    )
    suspend fun updateDrug(
        primaryKey: String,
        title: String?,
        trade_name: String?,
        pharmacological_name: String?,
        action: String?,
        antidote: String?,
        cautions: String?,
        contraindication: String?,
        dosages: String?,
        indications: String?,
        maximum_dose: String?,
        nursing_implications: String?,
        notes: String?,
        preparation: String?,
        side_effects: String?,
        video: String?,
        category_id: String?,
        subcategory_id: String?,
    ): Int


    /**
     * Returns all drug records in cache [List<DrugCacheEntity>]
     */
    @Query("SELECT * FROM drugs")
    suspend fun getAllDrugs(): List<DrugCacheEntity>


    /**
     *  Returns all drug records in the cache Oder by title ASC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where title LIKE '%' || :query
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchAllOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    /**
     *  Returns all drug records in the cache Oder by title DESC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where title LIKE '%' || :query
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchAllOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    /**
     *  Returns drug records where categoryId in the cache Oder by title ASC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where (title LIKE '%' || :query) AND category_id= :categoryId
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereCategoryOrderByTitleASC(
        query: String,
        categoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    /**
     *  Returns drug records where categoryId in the cache Oder by title DESC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where (title LIKE '%' || :query) AND category_id= :categoryId
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereCategoryOrderByTitleDESC(
        query: String,
        categoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>

    /**
     *  Returns drug records where subcategoryId in the cache Oder by title ASC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where (title LIKE '%' || :query) AND subcategory_id= :subcategoryId
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereSubcategoryOrderByTitleASC(
        query: String,
        subcategoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    /**
     *  Returns drug records where subcategoryId in the cache Oder by title DESC [List<DrugCacheEntity>]
     */
    @Query(
        """
        SELECT * FROM drugs
        where (title LIKE '%' || :query) AND subcategory_id= :subcategoryId
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchWhereSubcategoryOrderByTitleDESC(
        query: String,
        subcategoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    /***
     * Get Filtered and Ordered Search query data [List<DrugCacheEntity>]
     */
    suspend fun returnOrderedQuery(
        query: String,
        categoryId: String?,
        subcategoryId: String?,
        filterAndOrder: OrderEnum,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity> {

        return when (filterAndOrder) {
            OrderEnum.ORDER_ASC -> {
                when {
                    subcategoryId != null -> {
                        searchWhereSubcategoryOrderByTitleASC(
                            query = query,
                            subcategoryId = subcategoryId,
                            page = page,
                            pageSize = pageSize,
                        )
                    }
                    categoryId != null -> {
                        searchWhereCategoryOrderByTitleASC(
                            query = query,
                            categoryId = categoryId,
                            page = page,
                            pageSize = pageSize,
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
                    subcategoryId != null -> {
                        searchWhereSubcategoryOrderByTitleDESC(
                            query = query,
                            subcategoryId = subcategoryId,
                            page = page,
                            pageSize = pageSize,
                        )
                    }
                    categoryId != null -> {
                        searchWhereCategoryOrderByTitleDESC(
                            query = query,
                            categoryId = categoryId,
                            page = page,
                            pageSize = pageSize,
                        )
                    }
                    else -> {
                        searchAllOrderByTitleDESC(
                            query = query,
                            page = page,
                            pageSize = pageSize,
                        )
                    }
                }
            }
        }

    }


    /***
     * Returns all number of drug records [Int]
     */
    @Query("SELECT COUNT(*) FROM drugs")
    fun getNumAllDrugs(): Int


    /***
     * Returns number of drug records in the cache of given category [Int]
     */
    @Query(
        """
       SELECT COUNT(*) FROM drugs
       WHERE category_id= :categoryId
    """
    )
    suspend fun getNumDrugsInCategory(categoryId: String): Int


    /***
     * Returns number of drug records in the cache of given subcategory [Int]
     */
    @Query(
        """
       SELECT COUNT(*) FROM drugs
       WHERE subcategory_id= :subcategoryId
    """
    )
    suspend fun getNumDrugsInSubcategory(subcategoryId: String): Int

    /***
     * Returns filtered Number of Drug records in the cache [Int]
     */
    suspend fun getNumDrugs(
        categoryId: String?,
        subcategoryId: String?
    ): Int {
        return when {
            subcategoryId != null -> {
                getNumDrugsInSubcategory(subcategoryId)
            }

            categoryId != null -> {
                getNumDrugsInCategory(categoryId)
            }

            else -> getNumAllDrugs()
        }
    }


}