package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Configuration
    @Repository
    interface StudentRepository : JpaRepository<Student?, Int?> {

        fun save(student: Student): Student
        override fun findAll(): List<Student>
        override fun findById(id: Int): Optional<Student?>
        fun existdById(id: Int): Boolean

    }
