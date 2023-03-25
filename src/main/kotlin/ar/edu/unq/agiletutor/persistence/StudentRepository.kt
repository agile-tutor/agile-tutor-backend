package ar.edu.unq.agiletutor.persistence

import ar.edu.unq.agiletutor.model.Alumno
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository




    @Configuration
    @Repository
    interface StudentRepository : JpaRepository<Alumno?, Int?> {

        fun save(student: Alumno): Alumno
        override fun findAll(): List<Alumno>

    }
