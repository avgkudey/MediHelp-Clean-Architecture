package com.teracode.medihelp.framework.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teracode.medihelp.framework.datasource.cache.model.DrugCacheEntity

const val DRUG_ORDER_ASC: String = ""
const val DRUG_ORDER_DESC: String = "-"
const val DRUG_FILTER_TITLE = "title"
const val DRUG_FILTER_DATE_CREATED = "created_at"

const val ORDER_BY_ASC_DATE_UPDATED = DRUG_ORDER_ASC + DRUG_FILTER_DATE_CREATED
const val ORDER_BY_DESC_DATE_UPDATED = DRUG_ORDER_DESC + DRUG_FILTER_DATE_CREATED
const val ORDER_BY_ASC_TITLE = DRUG_ORDER_ASC + DRUG_FILTER_TITLE
const val ORDER_BY_DESC_TITLE = DRUG_ORDER_DESC + DRUG_FILTER_TITLE

const val DRUG_PAGINATION_PAGE_SIZE = 30

@Dao
interface DrugDao {

    @Insert
    suspend fun insertDrug(drug: DrugCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDrugs(drugs: List<DrugCacheEntity>): LongArray

    @Query("SELECT * FROM drugs WHERE id= :id LIMIT 1")
    suspend fun searchDrugById(id: String): DrugCacheEntity?

    @Query("DELETE FROM drugs WHERE id= :primaryKey")
    suspend fun deleteDrug(primaryKey: String): Int

    @Query("DELETE FROM drugs WHERE id IN (:ids)")
    suspend fun deleteDrugs(ids: List<String>): Int

    @Query("DELETE FROM drugs")
    suspend fun deleteAllDrugs()

    @Query("SELECT * FROM drugs")
    suspend fun getAllDrugs(): List<DrugCacheEntity>

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


    @Query("SELECT * FROM drugs")
    suspend fun searchDrugs(): List<DrugCacheEntity>


    @Query(
        """
        SELECT * FROM drugs
        where title LIKE '%' || :query
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>

    @Query(
        """
        SELECT * FROM drugs
        where title LIKE '%' || :query
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    @Query(
        """
        SELECT * FROM drugs
        where category_id = :categoryId
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsWhereCategoryOrderByTitleDESC(
        categoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>

    @Query(
        """
        SELECT * FROM drugs
        where category_id = :categoryId
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsWhereCategoryOrderByTitleASC(
        categoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    @Query(
        """
        SELECT * FROM drugs
        where subcategory_id = :subcategoryId
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsWhereSubcategoryOrderByTitleDESC(
        subcategoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>

    @Query(
        """
        SELECT * FROM drugs
        where subcategory_id = :subcategoryId
        ORDER BY title ASC LIMIT (:page * :pageSize)
    """
    )
    suspend fun searchDrugsWhereSubcategoryOrderByTitleASC(
        subcategoryId: String,
        page: Int,
        pageSize: Int = DRUG_PAGINATION_PAGE_SIZE
    ): List<DrugCacheEntity>


    @Query("SELECT COUNT(*) FROM drugs")
    fun getNumDrugs(): Int


    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<DrugCacheEntity> {
        return ArrayList()
    }
}