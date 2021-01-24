package com.teracode.medihelp.quizmodule.framework.datasource.cache.implementation

import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.cache.abstraction.QuizDaoService
import com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers.QuizCacheMapper
import com.teracode.medihelp.quizmodule.framework.datasource.database.QuizDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizDaoServiceImpl
@Inject
constructor(
    private val dao: QuizDao,
    private val mapper: QuizCacheMapper,

    ) : QuizDaoService {
    override suspend fun insertQuiz(quiz: Quiz): Long {
        return dao.insertQuiz(quiz = mapper.mapToEntity(quiz))
    }

    override suspend fun insertQuizzes(quizzes: List<Quiz>): LongArray {
        return dao.insertQuizzes(quizzes = mapper.quizListToEntityList(quizzes))
    }

    override suspend fun searchQuizById(id: String): Quiz? {
        return dao.searchQuizById(id)?.let {
            mapper.mapFromEntity(it)
        }
    }

    override suspend fun deleteQuiz(primaryKey: String): Int {
        return dao.deleteQuiz(primaryKey = primaryKey)
    }

    override suspend fun deleteQuizzes(quizzes: List<Quiz>): Int {
        val ids = quizzes.mapIndexed { _, value -> value.id }

        return dao.deleteQuizzes(ids)

    }

    override suspend fun deleteAllQuizzes() {
        return dao.deleteAllQuizzes()
    }

    override suspend fun updateQuiz(
        primaryKey: String,
        name: String,
        description: String?,
        image: String?,
        level: String?,
        visibility: String?,
        questions: Long,
    ): Int {
        return dao.updateQuiz(
            primaryKey = primaryKey,
            name = name,
            description = description,
            image = image,
            level = level,
            visibility = visibility,
            questions = questions,
        )
    }

    override fun getNumQuizzes(): Int {
        return dao.getNumQuizzes()
    }

    override suspend fun getAllQuizzes(): List<Quiz> {
        return mapper.entityListToQuizList(entities = dao.getAllQuizzes())
    }
}