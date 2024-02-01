package ar.edu.unq.agiletutor.service

import ar.edu.unq.agilemeeting.service.SurveyDataDTO
import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Survey
import ar.edu.unq.agiletutor.persistence.StudentRepository
import ar.edu.unq.agiletutor.persistence.SurveyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentService {

    @Autowired
    private lateinit var repository: StudentRepository

    @Autowired
    private lateinit var surveyRepository: SurveyRepository

    @Transactional
    fun register(student: Student): Student {

        if (existStudent(student)) {
            throw UsernameExistException("Student with email:  ${student.email} is used")
        }

        return repository.save(student)
    }


    @Transactional
    fun registerMany(students: List<Student>): List<Student> {

        if (students.any { existStudent(it) }) {
            throw UsernameExistException("There is a Student with an used email")
        }

        return repository.saveAll(students.asIterable()).toMutableList()
    }


    @Transactional
    fun findAll(): List<Student> {
        return repository.findAll()
    }

    private fun existStudent(student: Student): Boolean {
        val students = repository.findAll().toMutableList()
        return students.any { it.email == student.email }
    }

    @Transactional
    fun findByID(id: Long): Student {
        val student = repository.findById(id)
        if (!(student.isPresent)) {
            throw ItemNotFoundException("Student with Id:  $id not found")
        }
        return student.get()
    }

    @Transactional
    fun findByName(name: String): List<Student> {
        val students = repository.findAll()
        return students.filter { (it.name == name) } ?: throw ItemNotFoundException("Not found student")
    }

    @Transactional
    fun findByEmail(email: String): List<Student> {
        val students = repository.findAll()
        return students.filter { (it.email == email) } ?: throw ItemNotFoundException("Not found student")
    }

    @Transactional
    fun updateattendances(studentId: Long, attendances: List<AttendanceDTO>): Student {
        val student = findByID(studentId)
        student.attendances = attendances.map { it.aModelo() }.toMutableSet()
        return repository.save(student)
    }

    @Transactional
    fun update(id: Long, entity: StudentDTO): StudentDTO {
        val student = findByID(id)
        student.name = entity.name
        student.surname = entity.surname
        student.identifier = entity.identifier
        student.email = entity.email
        student.observations = entity.observations!!
        return StudentDTO.desdeModelo(repository.save(student))
    }

    fun attendancesFromAStudent(id: Long): Set<Attendance> {
        val student = findByID(id)
        return student.attendances
    }

    fun attendancesPercentageFromAStudent(id: Long): Double {
        val student = findByID(id)
        return student.attendancePercentage()
    }

    @Transactional
    fun averageAttendancesFromAllStudents(): Double {
        if (findAll().isNotEmpty()) {
            return findAll().sumOf { it.attendancePercentage() } / findAll().size
        } else {
            return 0.0
        }
    }

    @Transactional
    fun studentsWirhoutAbsents(): List<Student> {
        return findAll().filter { it.sinFaltas() }
    }

    @Transactional
    fun studentsWithAbsents(): List<Student> {
        return findAll().filter { !it.sinFaltas() }
    }

    @Transactional
    fun attendedDays(id: Long): List<Attendance> {
        val student = findByID(id)
        return student.attended()
    }

    @Transactional
    fun absentdDays(id: Long): List<Attendance> {
        val student = findByID(id)
        return student.absent()
    }

    @Transactional
    fun studentsAttendedAtAParticularDay(day: Int): List<Student> {
        val rangedays = (1..6)
        if (!rangedays.contains(day)) {
            throw ItemNotFoundException(" Day:  $day invalid")
        }
        return findAll().filter { it.attendedDay(day) }
    }


    @Transactional
    fun studentsAbsentAtAParticularDay(day: Int): List<Student> {
        val rangedays = (1..6)
        if (!rangedays.contains(day)) {
            throw ItemNotFoundException(" Day:  $day invalid")
        }
        return findAll().filter { !it.attendedDay(day) }
    }

    @Transactional
    fun studentsNotBlockedAbsentAtAParticularDay(courseId: Long, day: Int): List<Student> {
        val rangedays = (1..6)
        if (!rangedays.contains(day)) {
            throw ItemNotFoundException(" Day:  $day invalid")
        }
        return findAll().filter { it.course!!.id == courseId && (!it.attendedDay(day)) && (!it.blocked) }
    }


    @Transactional
    fun blockOrUnblockAStudent(id: Long, blocked: Boolean): StudentDTO {
        val student = findByID(id)
        student.blocked = blocked
        return StudentDTO.desdeModelo(repository.save(student))
    }


    @Transactional
    fun deleteById(id: Long) {
        val student = repository.findById(id)
        if (!(student.isPresent)) {

            throw ItemNotFoundException("Student with Id:  $id not found")
        }
        repository.deleteById(id)
    }

    @Transactional
    fun checkMail(email: String): Boolean {
        val studentsemails = repository.findAll().toMutableList().map { it.email }
        return studentsemails.contains(email)
    }

    @Transactional
    fun saveStudentSurvey(studentId: Long, surveyDTO: SurveyDataDTO): Survey {
        val survey = Survey(studentId,
                surveyDTO.ciudad,
                surveyDTO.wifi,
                surveyDTO.datos,
                surveyDTO.pc,
                surveyDTO.notebook,
                surveyDTO.celular,
                surveyDTO.tablet,
                surveyDTO.entorno,
                surveyDTO.exclusividad,
                surveyDTO.estado)
        println(survey.toString())
        return surveyRepository.save(survey)
    }
}