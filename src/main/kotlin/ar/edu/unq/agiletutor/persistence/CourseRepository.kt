package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Course
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Configuration
@Repository
interface CourseRepository: JpaRepository<Course?, Int?> {

    fun save(course: Course): Course
    override fun findAll(): List<Course>
}