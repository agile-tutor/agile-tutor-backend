package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Alumno
import ar.edu.unq.agiletutor.model.Notifyer
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface NotifyerRepository : JpaRepository<Notifyer?, Int?> {


}