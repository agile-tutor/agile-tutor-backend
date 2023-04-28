package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TutorService {

    @Autowired
    private lateinit var repository: TutorRepository

    @Transactional
    fun register(tutor: Tutor): Tutor {

        if (existByEmail(tutor.email!!)) {
            throw UsernameExistException("Tutor with email:  ${tutor.email} is used")
        }

        return repository.save(tutor)
    }

    @Transactional
    fun findAll(): List<Tutor> {
        return repository.findAll()
    }

    private fun existByEmail(email: String): Boolean {
        val tutores = repository.findAll().toMutableList()
        return tutores.any { it.email == email }
    }

    @Transactional
    fun login(email: String, password: String): Tutor {
        val tutors = repository.findAll()
        return tutors.find { (it.email == email) && (it.password == password) }
            ?: throw ItemNotFoundException("Not found user")
    }

    @Transactional
    fun findByID(id: Int): Tutor {
        val tutor = repository.findById(id)
        if (!(tutor.isPresent)) {
            throw ItemNotFoundException("Tutor with Id:  $id not found")
        }
        return tutor.get()
    }

    @Transactional
    fun findByEmail(email: String): Tutor {
        val tutors = repository.findAll()
        return tutors.find { (it.email == email) } ?: throw ItemNotFoundException("Not found Tutor")
    }

    @Transactional
    fun deleteById(id: Int) {
        val tutor = repository.findById(id)
        if (!(tutor.isPresent)) {
            throw ItemNotFoundException("Tutor with Id:  $id not found")
        }
        repository.deleteById(id)
    }

    @Transactional
    fun update(id: Int, entity: TutorRegisterDTO): Tutor {
        // if (! repository.existsById(id))
        //    {throw ItemNotFoundException("Tutor with Id:  $id not found") }
        val tutor = findByID(id)
        tutor.email = entity.email
        tutor.name = entity.name
        tutor.surname = entity.surname
        tutor.password = entity.password
        return repository.save(tutor)
    }

    @Transactional
    fun coursesFromATutor(id: Int): MutableList<Course> {
        val tutor = findByID(id)
        return tutor.courses.toMutableList()
    }
}