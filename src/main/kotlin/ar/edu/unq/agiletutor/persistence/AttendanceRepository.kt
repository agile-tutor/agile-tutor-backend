package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Attendance
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface AttendanceRepository : CrudRepository<Attendance?, Int?> {
    fun save(attendance: Attendance): Attendance
    fun saveAll(attendances: List<Attendance>): List<Attendance>
    override fun findAll(): List<Attendance>
}

