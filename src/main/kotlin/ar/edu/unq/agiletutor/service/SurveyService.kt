package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.Survey
import ar.edu.unq.agiletutor.persistence.SurveyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService{

 @Autowired
 private lateinit var surveyRepository: SurveyRepository

    @Transactional
    fun findAll(): List<Survey> {
        return surveyRepository.findAll()
    }
}