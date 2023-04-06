package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TutorService {

    @Autowired
    private  lateinit var repository: TutorRepository


    @Transactional
    fun register(tutor: Tutor): Tutor {

        if ( existStudent(tutor) )  {
            throw UsernameExistException("Tutor with email:  ${tutor.email} is used")
        }

        val savedTutor= repository.save(tutor)
        return savedTutor
    }

   @Transactional
    fun findAll(): List<Tutor> {
        val tutores =  repository.findAll()
        return tutores
    }

    private fun existStudent(tutor: Tutor): Boolean {
        var bool = false
        val tutores = repository.findAll().toMutableList()
        if ( tutores.isNotEmpty() ) {
            bool =  tutores.any { it.email == tutor.email }
        }
        return bool
    }
}