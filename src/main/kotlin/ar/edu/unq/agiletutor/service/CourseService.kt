package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService {

    @Autowired
    private  lateinit var tutorService: TutorService

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


    @Transactional
    fun findByID(id: Int): Course {
        val course =  repository.findById(id)
        if ( ! (course.isPresent ))
        {throw ItemNotFoundException("Course with Id:  $id not found") }
        val newCourse=  course.get()
        return newCourse
    }


    @Transactional
    fun tutorFromACourse(id: Int):Tutor {
        val course =  findByID(id)
       return course.tutor!!
    }

    fun studentsFromACourse(id:Int): List<Student>{
        val course = findByID(id)
        return course.students.toMutableList()
    }


    fun studentsFromATutor(id:Int): List<Student>{
        val students = mutableListOf<Student>()
        val courses = tutorService.coursesFromATutor(id)
        for (course in courses){
            students.addAll ( studentsFromACourse(course.id!!) )
        }
        return students
    }


    @Transactional
    fun updateStudentsAttendancesFromACourse(id:Int, day:Int, booleans:List<Boolean>) {
        val course = findByID(id)
        for (student in course.students) {
            val boolean = booleans.iterator().next()
            updateAttendance(day,boolean, student.attendances.toMutableList())
            repository.save(course)
           }
    }

      private fun updateAttendance(day:Int,boolean:Boolean, attendances:MutableList<Attendance>){
          val attendance = attendances.get(day)
          attendance.attended = boolean
          attendances.set(day, attendance)

      }
    }


