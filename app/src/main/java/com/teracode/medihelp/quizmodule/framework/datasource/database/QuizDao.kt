package com.teracode.medihelp.quizmodule.framework.datasource.database

import androidx.room.*
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuizCacheEntity


@Dao
interface QuizDao {

    /**
     * Inserts a single QuizCacheEntity model in to the cache [Long]
     */
    @Insert
    suspend fun insertQuiz(quiz: QuizCacheEntity): Long

    /**
     * Inserts multiple QuizCacheEntity models in to the cache (onConflict - ignore)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuizzes(quizzes: List<QuizCacheEntity>): LongArray


    /**
     * Returns QuizCacheEntity model of given ID  [QuizCacheEntity]?
     */
    @Query("SELECT * FROM quizzes WHERE id= :id LIMIT 1")
    suspend fun searchQuizById(id: String): QuizCacheEntity?


    /**
     * Deletes Quiz record in the cache of given ID [Int]
     */
    @Query("DELETE FROM quizzes WHERE id= :primaryKey")
    suspend fun deleteQuiz(primaryKey: String): Int

    /**
     * Deletes multiple quizzes records in the cache of given IDS [Int]
     */
    @Query("DELETE FROM quizzes WHERE id IN (:ids)")
    suspend fun deleteQuizzes(ids: List<String>): Int


    /**
     * Deletes all quizzes records in the cache
     */
    @Query("DELETE FROM quizzes")
    suspend fun deleteAllQuizzes()

    /**
     * Updates Quiz record in the cache [Int]
     */
    @Query(
        """
        UPDATE quizzes
        SET
            name= :name,
            description= :description,
            image= :image,
            level= :level,
            visibility= :visibility,
            questions= :questions
        WHERE id = :primaryKey
    """
    )
    suspend fun updateQuiz(
        primaryKey: String,
        name: String,
        description: String?,
        image: String?,
        level: String?,
        visibility: String?,
        questions: Long = 0,
    ): Int


    /***
     * Returns Number of Quizzes in the cache
     */
    @Query("SELECT COUNT(*) FROM quizzes")
    fun getNumQuizzes(): Int


    /**
     * Returns all Quiz records in cache [List<QuizCacheEntity>]
     */
    @Query("SELECT * FROM quizzes ORDER BY name ASC")
    suspend fun getAllQuizzes(): List<QuizCacheEntity>


}