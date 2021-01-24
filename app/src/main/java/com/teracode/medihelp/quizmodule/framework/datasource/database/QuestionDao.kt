package com.teracode.medihelp.quizmodule.framework.datasource.database

import androidx.room.*
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuestionCacheEntity


@Dao
interface QuestionDao {

    /**
     * Inserts a single QuestionCacheEntity model in to the cache [Long]
     */
    @Insert
    suspend fun insertQuestion(question: QuestionCacheEntity): Long

    /**
     * Inserts multiple QuestionCacheEntity models in to the cache (onConflict - ignore)
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(questions: List<QuestionCacheEntity>): LongArray


    /**
     * Returns QuestionCacheEntity model of given ID  [QuestionCacheEntity]?
     */
    @Query("SELECT * FROM questions WHERE id= :id LIMIT 1")
    suspend fun searchQuestionById(id: String): QuestionCacheEntity?


    /**
     * Deletes Question record in the cache of given ID [Int]
     */
    @Query("DELETE FROM questions WHERE id= :primaryKey")
    suspend fun deleteQuestion(primaryKey: String): Int

    /**
     * Deletes multiple questions records in the cache of given IDS [Int]
     */
    @Query("DELETE FROM questions WHERE id IN (:ids)")
    suspend fun deleteQuestions(ids: List<String>): Int


    /**
     * Deletes all questions records in the cache
     */
    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()

    /**
     * Updates Question record in the cache [Int]
     */
    @Query(
        """
        UPDATE questions
        SET
            question= :question,
            quizId= :quizId,
            answer= :answer,
            option_a= :option_a,
            option_b= :option_b,
            option_c= :option_c,
            option_d= :option_d,
            timer= :timer
        WHERE id = :primaryKey
    """
    )
    suspend fun updateQuestion(
        primaryKey: String,
        quizId: String,
        question: String,
        answer: String?,
        option_a: String?,
        option_b: String?,
        option_c: String?,
        option_d: String?,
        timer: Long = 0,
    ): Int


    /***
     * Returns Number of Questions in the cache
     */
    @Query("SELECT COUNT(*) FROM questions")
    fun getNumQuestions(): Int


    /**
     * Returns all Question records in cache related to Quiz [List<QuestionCacheEntity>]
     */
    @Query("SELECT * FROM questions WHERE quizId=:quizId ORDER BY question ASC")
    suspend fun getAllQuestionsByQuiz(quizId: String): List<QuestionCacheEntity>

    /**
     * Returns all Question records in cache [List<QuestionCacheEntity>]
     */
    @Query("SELECT * FROM questions ORDER BY question ASC")
    suspend fun getAllQuestions(): List<QuestionCacheEntity>


}