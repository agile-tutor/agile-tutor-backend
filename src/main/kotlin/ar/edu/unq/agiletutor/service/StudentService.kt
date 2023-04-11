package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.persistence.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentService {

    @Autowired
    private  lateinit var repository: StudentRepository


    @Transactional
    fun register(student: Student): Student {

        if ( existStudent(student) )  {
            throw UsernameExistException("Student with email:  ${student.email} is used")
        }

        val savedStudent = repository.save(student)
        return savedStudent
         }


    @Transactional
    fun findAll(): List<Student> {
        val students =  repository.findAll()
        return students
    }

    private fun existStudent(student:Student): Boolean {
            var bool = false
            val students = repository.findAll().toMutableList()
            if ( students.isNotEmpty() ) {
                bool =  students.any { it.email == student.email }
            }
            return bool
        }


    @Transactional
    fun findByID(id: Int): Student {
        val student =  repository.findById(id)
        if ( ! (student.isPresent ))
        {throw ItemNotFoundException("Student with Id:  $id not found") }
        val newStudent=  student.get()
        return newStudent
    }

    @Transactional
    fun findByName(name: String): List<Student> {
        val students= repository.findAll()
        return students.filter { (it.name == name)} ?: throw ItemNotFoundException("Not found student")

    }


     @Transactional
    fun  updateattendances(student:Student,attendances:List <AttendanceDTO>):Student{
        student.attendances = attendances.map { it.aModelo() }.toMutableSet()
        student.attendancepercentage = calcularPorcentajeDeAsistencias(attendances)
        return repository.save (student)
    }

    private fun calcularPorcentajeDeAsistencias (attendances:List <AttendanceDTO>) : Double{
       var  count = 0.0

        for (attendance in attendances) {
           if (attendance.check) {count ++  }
        }
        return (count * (100/6))
    }

    @Transactional
    fun update(id: Int , entity: StudentDTO) : Student {
        if (! repository.existdById(id))
        {throw ItemNotFoundException("Student with Id:  $id not found") }
        return  repository.save (entity.aModelo())
    }
}