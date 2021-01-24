package com.teracode.medihelp.quizmodule.business.interactors.splash

import android.content.SharedPreferences
import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.network.ApiResponseHandler
import com.teracode.medihelp.business.data.util.safeApiCall
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.DataState
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager.Companion.QUESTION_LIST_SYNCED
import com.teracode.medihelp.framework.presentation.splash.DrugsNetworkSyncManager.Companion.QUIZ_LIST_SYNCED
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuestionCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.data.network.abstraction.QuestionNetworkDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.business.domain.model.QuestionValidator
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncQuestions(
    private val questionCacheDataSource: QuestionCacheDataSource,
    private val quizCacheDataSource: QuizCacheDataSource,
    private val networkDataSource: QuestionNetworkDataSource,
    private val editor: SharedPreferences.Editor,
    private val validator: QuestionValidator,
) {

    suspend fun syncCategories() {
        val cachedQuizzesList = getCachedQuizzesList()
        val cachedQuestionsList = getCachedQuestionsList()

        val networkQuestionsList = getNetworkQuestions(ArrayList(cachedQuizzesList))


        syncNetworkQuizzesWithCachedQuizzes(ArrayList(cachedQuestionsList),
            ArrayList(networkQuestionsList))
    }

    private suspend fun syncNetworkQuizzesWithCachedQuizzes(
        cachedQuestions: ArrayList<Question>,
        networkQuestions: ArrayList<Question>,
    ) =
        withContext(IO) {

            printLogD("SyncQuestions", "Started")

            for (networkQuestion in networkQuestions) {

                if (validator.validate(networkQuestion)) {

                    questionCacheDataSource.searchQuestionById(networkQuestion.id)
                        ?.let { cachedQuestion ->

                            cachedQuestions.remove(cachedQuestion)
                            checkRequiresUpdate(cachedQuestion, networkQuestion)

                        } ?: questionCacheDataSource.insertQuestion(networkQuestion)
                }



                for (cachedQuestion in cachedQuestions) {
                    if (validator.validate(networkQuestion)) {
                        networkDataSource.searchQuestion(cachedQuestion)
                            ?: questionCacheDataSource.deleteQuestion(
                                cachedQuestion.id
                            )
                    } else {
                        questionCacheDataSource.deleteQuestion(
                            cachedQuestion.id
                        )
                    }
                }
            }

            printLogD("SyncQuestions", "Finished")
            setSynced(QUESTION_LIST_SYNCED, true)
        }

    private suspend fun getNetworkQuestions(cachedQuizzes: ArrayList<Quiz>): List<Question> {
        val networkQuestions: ArrayList<Question> = ArrayList()

        for (cachedQuiz in cachedQuizzes) {
            val networkResult = safeApiCall(IO) {
                networkDataSource.getAllQuestions(cachedQuiz.id)
            }

            val response = object : ApiResponseHandler<List<Question>, List<Question>>(
                response = networkResult,
                stateEvent = null
            ) {
                override suspend fun handleSuccess(resultObj: List<Question>): DataState<List<Question>>? {

                    return DataState.data(
                        response = null,
                        data = resultObj,
                        stateEvent = null
                    )
                }

            }.getResult()

            val networkDataList = response?.data ?: ArrayList()

            networkQuestions.addAll(networkDataList)
        }



        return networkQuestions
    }

    private suspend fun checkRequiresUpdate(cachedQuestion: Question, networkQuestion: Question) {

        if (networkQuestion != cachedQuestion) {


            safeCacheCall(IO) {
                questionCacheDataSource.updateQuestion(
                    primaryKey = networkQuestion.id,
                    quizId = networkQuestion.quizId,
                    question = networkQuestion.question,
                    answer = networkQuestion.answer,
                    option_a = networkQuestion.option_a,
                    option_b = networkQuestion.option_b,
                    option_c = networkQuestion.option_c,
                    option_d = networkQuestion.option_d,
                    timer = networkQuestion.timer,
                )
            }


        }
    }

    private suspend fun getCachedQuizzesList(): List<Quiz> {

        val cachedResult = safeCacheCall(IO) {
            quizCacheDataSource.getAllQuizzes()
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

    private suspend fun getCachedQuestionsList(): List<Question> {

        val cachedResult = safeCacheCall(IO) {
            questionCacheDataSource.getAllQuestions()
        }

        val response = object : CacheResponseHandler<List<Question>, List<Question>>(
            response = cachedResult,
            stateEvent = null
        ) {
            override suspend fun handleSuccess(resultObj: List<Question>): DataState<List<Question>>? {

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