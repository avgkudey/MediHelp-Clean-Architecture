package com.teracode.medihelp.quizmodule.business.interactors.splash

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager.Companion.QUIZ_LIST_SYNCED
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuizNetworkDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncQuizzes(
    private val cacheDataSource: QuizCacheDataSource,
    private val networkDataSource: QuizNetworkDataSource,
    private val editor: SharedPreferences.Editor,
) {

    suspend fun syncCategories() {
        val cachedQuizzesList = getCachedCategoriesList()


        syncNetworkQuizzesWithCachedQuizzes(ArrayList(cachedQuizzesList))
    }

    private suspend fun syncNetworkQuizzesWithCachedQuizzes(cachedQuizzes: ArrayList<Quiz>) =
        withContext(IO) {
            val networkResult = safeApiCall(IO) {
                networkDataSource.getAllQuizzes()
            }
            printLogD("printLogD", "Quiz Sync Started")
            val response = object : ApiResponseHandler<List<Quiz>, List<Quiz>>(
                response = networkResult,
                stateEvent = null
            ) {
                override suspend fun handleSuccess(resultObj: List<Quiz>): DataState<List<Quiz>>? {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()

            val networkQuizList = response?.data ?: ArrayList()

            for (quiz in networkQuizList) {
                cacheDataSource.searchQuizById(quiz.id)?.let { cachedQuiz ->

                    cachedQuizzes.remove(quiz)
                    checkRequiresUpdate(cachedQuiz, quiz)

                } ?: cacheDataSource.insertQuiz(quiz)

                for (cachedQuiz in cachedQuizzes) {
                    networkDataSource.searchQuiz(cachedQuiz)
                        ?: cacheDataSource.deleteQuiz(
                            cachedQuiz.id
                        )
                }
            }


            setSynced(QUIZ_LIST_SYNCED, true)
        }

    private suspend fun checkRequiresUpdate(cachedQuiz: Quiz, networkQuiz: Quiz) {

        if (networkQuiz != cachedQuiz) {


            safeCacheCall(IO) {
                cacheDataSource.updateQuiz(
                    primaryKey = networkQuiz.id,
                    name = networkQuiz.name,
                    description = networkQuiz.description,
                    image = networkQuiz.image,
                    level = networkQuiz.level,
                    visibility = networkQuiz.visibility,
                    questions = networkQuiz.questions,
                )
            }


        }
    }

    private suspend fun getCachedCategoriesList(): List<Quiz> {

        val cachedResult = safeCacheCall(IO) {
            cacheDataSource.getAllQuizzes()
        }

        val response = object : CacheResponseHandler<List<Quiz>, List<Quiz>>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Quiz>): DataState<List<Quiz>>? {

                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()

        return response?.data ?: ArrayList()
    }


    private fun setSynced(key: String, value: Boolean) {

        editor.putBoolean(key, value)
        editor.apply()
    }
}