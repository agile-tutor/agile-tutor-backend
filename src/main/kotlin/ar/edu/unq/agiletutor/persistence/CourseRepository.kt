package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface CourseRepository: CrudRepository<Course?, Int?> {

    fun save(course: Course): Course
    override fun findAll(): List<Course>
    override fun findById(id: Int): Optional<Course?>
}