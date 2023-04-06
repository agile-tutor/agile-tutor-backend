package ar.edu.unq.agiletutor.persistence


import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface TutorRepository : JpaRepository<Tutor?, Int?> {

    fun save(tutor: Tutor): Tutor
    override fun findAll(): List<Tutor>

}