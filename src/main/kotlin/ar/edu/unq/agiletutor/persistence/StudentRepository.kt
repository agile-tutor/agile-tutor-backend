package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Student
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface StudentRepository : CrudRepository<Student?, Long?> {

    fun save(student: Student): Student
    override fun findAll(): List<Student>
    override fun findById(id: Long): Optional<Student?>
    override fun existsById(id: Long): Boolean
   // @Query("SELECT s FROM Student s inner join Attendance a on a.id= ?1 and s.course.id= ?2 where a.attended=false")
    //fun findAbsentByDayAndCourse(day: Int, courseID: Int): List<Student>
}