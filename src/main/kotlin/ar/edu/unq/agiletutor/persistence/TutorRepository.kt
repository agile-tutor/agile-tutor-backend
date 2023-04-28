package ar.edu.unq.agiletutor.persistence


import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface TutorRepository : CrudRepository<Tutor?, Int?> {

    fun save(tutor: Tutor): Tutor
    override fun findAll(): List<Tutor>
    override fun findById(id: Int): Optional<Tutor?>
    override fun existsById(id: Int): Boolean
    override fun deleteById(id: Int)

}