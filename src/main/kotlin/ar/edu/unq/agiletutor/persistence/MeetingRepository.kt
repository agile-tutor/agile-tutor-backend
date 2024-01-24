package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Meeting
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface MeetingRepository : CrudRepository<Meeting?, Int?> {

    fun save(meeting: Meeting): Meeting
    override fun findAll(): List<Meeting>
    override fun findById(id: Int): Optional<Meeting?>
//    fun findByName(name: String): Optional<Meeting?>
}