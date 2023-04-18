package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
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

    }
