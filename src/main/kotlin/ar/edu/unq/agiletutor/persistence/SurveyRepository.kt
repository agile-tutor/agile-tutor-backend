package ar.edu.unq.agiletutor.persistence;

import java.util.*

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import ar.edu.unq.agiletutor.model.Survey;
import org.springframework.data.repository.CrudRepository

@Configuration
@Repository
interface SurveyRepository : CrudRepository<Survey?, Long?> {
    fun save(survey: Survey): Survey
    override fun findAll(): List<Survey>
}
