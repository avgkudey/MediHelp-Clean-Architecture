package com.teracode.medihelp.quizmodule.business.interactors.quizdetail

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizdetail.state.QuizDetailViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuiz(
    private val dataSource: QuizCacheDataSource,
) {

    fun getQuiz(
        quizId: String,
        stateEvent: StateEvent,
    ): Flow<DataState<QuizDetailViewState>?> = flow {


        val cacheResult = safeCacheCall(IO) {
            dataSource.searchQuizById(quizId)
        }


        val response = object : CacheResponseHandler<QuizDetailViewState, Quiz>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: Quiz): DataState<QuizDetailViewState> {
                var message: String? = SEARCH_QUIZ_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj == null) {
                    message = SEARCH_QUIZ_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }


                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ),
                    data = QuizDetailViewState(
                        quiz = resultObj
                    ),
                    stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(response)

    }


    companion object {
        const val SEARCH_QUIZ_SUCCESS = "Successfully retrieved quiz"
        const val SEARCH_QUIZ_NO_MATCHING_RESULTS = "There are no quizzes that match that query."
        const val SEARCH_QUIZ_FAILED = "Failed to retrieve the quiz."
    }
}