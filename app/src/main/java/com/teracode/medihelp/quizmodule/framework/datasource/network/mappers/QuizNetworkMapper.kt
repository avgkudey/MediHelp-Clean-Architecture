package com.teracode.medihelp.quizmodule.framework.datasource.network.mappers

import com.teracode.medihelp.business.domain.util.EntityMapper
import com.teracode.medihelp.framework.presentation.common.capitalizeWords
import com.teracode.medihelp.quizmodule.business.domain.model.Quiz
import com.teracode.medihelp.quizmodule.framework.datasource.network.QuizNetworkEntity
import javax.inject.Singleton

@Singleton
class QuizNetworkMapper : EntityMapper<QuizNetworkEntity, Quiz> {


    fun quizListToEntityList(quizzes: List<Quiz>): List<QuizNetworkEntity> {
        val list: ArrayList<QuizNetworkEntity> = ArrayList()
        for (quiz in quizzes) {
            list.add(mapToEntity(quiz))
        }
        return list
    }

    fun entityListToQuizList(entities: List<QuizNetworkEntity>): List<Quiz> {
        val list: ArrayList<Quiz> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }


    override fun mapFromEntity(entity: QuizNetworkEntity): Quiz {

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

    override fun mapToEntity(domainModel: Quiz): QuizNetworkEntity {
        return QuizNetworkEntity(
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