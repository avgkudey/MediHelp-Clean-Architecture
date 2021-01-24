package com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers

import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuizCacheEntity
import javax.inject.Singleton

@Singleton
class QuizCacheMapper : EntityMapper<QuizCacheEntity, Quiz> {


    fun quizListToEntityList(quizzes: List<Quiz>): List<QuizCacheEntity> {
        val list: ArrayList<QuizCacheEntity> = ArrayList()
        for (quiz in quizzes) {
            list.add(mapToEntity(quiz))
        }
        return list
    }

    fun entityListToQuizList(entities: List<QuizCacheEntity>): List<Quiz> {
        val list: ArrayList<Quiz> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: QuizCacheEntity): Quiz {

        return Quiz(
            id = entity.id,
            name = entity.name.capitalizeWords(),
            image = entity.image,
            description = entity.description,
            level = entity.level,
            visibility = entity.visibility,
            questions = entity.questions,
        )
    }

    override fun mapToEntity(domainModel: Quiz): QuizCacheEntity {
        return QuizCacheEntity(
            id = domainModel.id,
            name = domainModel.name.capitalizeWords(),
            image = domainModel.image,
            description = domainModel.description,
            level = domainModel.level,
            visibility = domainModel.visibility,
            questions = domainModel.questions,
        )
    }
}