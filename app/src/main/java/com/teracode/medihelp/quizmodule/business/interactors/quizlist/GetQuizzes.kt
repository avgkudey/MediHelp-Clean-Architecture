package com.teracode.medihelp.quizmodule.business.interactors.quizlist

import com.teracode.medihelp.business.data.cache.CacheResponseHandler
import com.teracode.medihelp.business.data.util.safeCacheCall
import com.teracode.medihelp.business.domain.state.*
import com.teracode.medihelp.quizmodule.business.data.cache.abstraction.QuizCacheDataSource
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.presentation.quizlist.state.QuizListViewState
import com.teracode.medihelp.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuizzes(
    private val dataSource: QuizCacheDataSource,
) {


    fun getQuizzes(stateEvent: StateEvent): Flow<DataState<QuizListViewState>?> = flow {

        val cacheResult = safeCacheCall(IO) {
            dataSource.getAllQuizzes()
        }

        val response = object : CacheResponseHandler<QuizListViewState, List<Quiz>>(
            response = cacheResult,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: List<Quiz>): DataState<QuizListViewState> {

                var message = GET_QUIZZES_SUCCESS
                var uiComponentType: UIComponentType = UIComponentType.None()


                if (resultObj.isEmpty()) {
                    message = GET_QUIZZES_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }

                printLogD(this::class.java.name, resultObj.toString())

                return DataState.data(
                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType,
                        messageType = MessageType.Success()
                    ), data = QuizListViewState(
                        quizList = ArrayList(resultObj)
                    ),
                    stateEvent = stateEvent
                )

            }

        }.getResult()

        emit(response)


    }

    companion object {
        const val GET_QUIZZES_SUCCESS = "Successfully retrieved list of quizzes"
        const val GET_QUIZZES_NO_MATCHING_RESULTS =
            "There are no quizzes that match that query."
        const val GET_QUIZZES_FAILED = "Failed to retrieve the list of quizzes."
    }

}