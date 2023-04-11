package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService {

    @Autowired
    private  lateinit var repository: CourseRepository


    @Transactional
    fun register(course: Course): Course {

        if ( existSCourse(course) )  {
            throw UsernameExistException("Course with name:  ${course.name} is used")
        }

        val savedCourse = repository.save(course)
        return savedCourse
    }

    private fun existSCourse(course: Course): Boolean {
        var bool = false
        val courses = repository.findAll().toMutableList()
        if ( courses.isNotEmpty() ) {
            bool =  courses.any { it.name == course.name}
        }
        return bool
    }

    @Transactional
    fun findAll(): List<Course> {
        val courses =  repository.findAll()
        return courses
    }
}