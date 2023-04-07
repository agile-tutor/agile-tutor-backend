package ar.edu.unq.agiletutor.persistence


import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface TutorRepository : JpaRepository<Tutor?, Int?> {

    fun save(tutor: Tutor): Tutor
    override fun findAll(): List<Tutor>
    override fun findById(id: Int): Optional<Tutor?>
    fun existdById(id: Int): Boolean
    override fun deleteById(id: Int)

}