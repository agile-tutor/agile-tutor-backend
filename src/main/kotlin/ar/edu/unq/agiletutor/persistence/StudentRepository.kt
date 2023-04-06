package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Student
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository




    @Configuration
    @Repository
    interface StudentRepository : JpaRepository<Student?, Int?> {

        fun save(student: Student): Student
        override fun findAll(): List<Student>

    }
