package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface TutorRepository : CrudRepository<Tutor?, Long?> {

    fun save(tutor: Tutor): Tutor
    override fun findAll(): List<Tutor>
    override fun findById(id: Long): Optional<Tutor?>
    override fun existsById(id: Long): Boolean
    override fun deleteById(id: Long)

}