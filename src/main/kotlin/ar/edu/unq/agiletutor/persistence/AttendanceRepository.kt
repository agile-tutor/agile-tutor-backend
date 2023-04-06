package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Asistencia
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Configuration
@Repository
interface AttendanceRepository : JpaRepository<Asistencia?, Int?> {

    @Modifying
    @Query("update Asistencia a set a.attended = ?1 where a.id = ?2")
    fun setAttendanceInfoById(attended: Boolean?, id: Int?)
}