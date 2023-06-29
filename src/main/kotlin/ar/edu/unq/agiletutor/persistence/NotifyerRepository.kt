package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Notifyer
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface NotifyerRepository : JpaRepository<Notifyer?, Int?> {

    override fun findAll(): List<Notifyer>
}