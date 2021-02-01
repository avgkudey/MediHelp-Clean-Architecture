package com.teracode.medihelp.quizmodule.business.interactors.quizstart

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuestionCacheDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.presentation.quizstart.state.QuizStartViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuestions(
    private val dataSource: QuestionCacheDataSource,
) {

    fun getQuestions(
        quizId: String,
        stateEvent: StateEvent,
    ): Flow<DataState<QuizStartViewState>?> = flow {


        val cacheResult = safeCacheCall(Dispatchers.IO) {
            dataSource.getAllQuestionsByQuiz(quizId)
        }


        val response = object : CacheResponseHandler<QuizStartViewState, List<Question>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Question>): DataState<QuizStartViewState>? {
                var message: String? = SEARCH_QUESTIONS_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()
                if (resultObj.isEmpty()) {
                    message = SEARCH_QUESTIONS_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }


                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ),
                    data = QuizStartViewState(
                        questionList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(response)

    }

    companion object {
        const val SEARCH_QUESTIONS_SUCCESS = "Successfully retrieved list of questions"
        const val SEARCH_QUESTIONS_NO_MATCHING_RESULTS =
            "There are no questions that match that query."
        const val SEARCH_QUESTIONS_FAILED = "Failed to retrieve the list of questions."
    }
}