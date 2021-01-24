package com.teracode.medihelp.quizmodule.business.domain.model

import com.teracode.medihelp.util.printLogD
import javax.inject.Singleton


@Singleton
class QuestionValidator {


    fun validate(question: Question): Boolean {
        printLogD("SyncQuestions", "${question.question} validating")
        if (question.question.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.question == null")
            return false
        }

        if (question.answer == null || question.answer.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.answer == null")
            return false
        }

        if (!(question.answer == "option_a" || question.answer == "option_b" || question.answer == "option_c" || question.answer == "option_d")) return false

        if (question.option_a == null || question.option_a.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.option_a == null")
            return false
        }
        if (question.option_b == null || question.option_b.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.option_b == null")
            return false
        }
        if (question.option_c == null || question.option_c.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.option_c == null")
            return false
        }
        if (question.option_d == null || question.option_d.isEmpty()) {
            printLogD("SyncQuestions", "${question.question} (question.option_d == null")
            return false
        }

        if (question.timer <= 0) {
            return false
        }

        printLogD("SyncQuestions", "${question.question} valid")

        return true
    }
}