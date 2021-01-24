package com.teracode.medihelp.quizmodule.framework.datasource.cache.mappers

import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.quizmodule.business.domain.model.Question
import com.teracode.medihelp.quizmodule.framework.datasource.cache.model.QuestionCacheEntity
import javax.inject.Singleton

@Singleton
class QuestionCacheMapper : EntityMapper<QuestionCacheEntity, Question> {


    fun questionListToEntityList(questions: List<Question>): List<QuestionCacheEntity> {
        val list: ArrayList<QuestionCacheEntity> = ArrayList()
        for (question in questions) {
            list.add(mapToEntity(question))
        }
        return list
    }

    fun entityListToQuestionList(entities: List<QuestionCacheEntity>): List<Question> {
        val list: ArrayList<Question> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: QuestionCacheEntity): Question {

        return Question(
            id = entity.id,
            quizId = entity.quizId,
            question = entity.question.capitalizeWords(),
            answer = entity.answer,
            option_a = entity.option_a,
            option_b = entity.option_b,
            option_c = entity.option_c,
            option_d = entity.option_d,
            timer = entity.timer,
        )
    }

    override fun mapToEntity(domainModel: Question): QuestionCacheEntity {
        return QuestionCacheEntity(
            id = domainModel.id,
            quizId = domainModel.quizId,
            question = domainModel.question.capitalizeWords(),
            answer = domainModel.answer,
            option_a = domainModel.option_a,
            option_b = domainModel.option_b,
            option_c = domainModel.option_c,
            option_d = domainModel.option_d,
            timer = domainModel.timer,
        )
    }
}