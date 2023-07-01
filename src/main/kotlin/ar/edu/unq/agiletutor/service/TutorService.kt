package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.*
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import ar.edu.unq.agiletutor.persistence.SurveyRepository
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TutorService {

    @Autowired
    private lateinit var repository: TutorRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var senderService: EmailServiceImpl

    @Autowired
    private lateinit var surveyService: SurveyService

   // @Autowired
   // private lateinit var surveyRepository: SurveyRepository


    @Transactional
    fun register(tutor: Tutor): Tutor {

        if (existByEmail(tutor.email!!)) {
            throw UsernameExistException("Tutor with email:  ${tutor.email} is used")
        }
        tutor.notifyer = Notifyer()
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
                ?: throw ItemNotFoundException("Not found tutor")
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

    @Transactional
    fun studentsFromATutor(id: Int): List<Student> {
        val students = mutableListOf<Student>()
        val tutor = findByID(id)
        val courses = tutor.courses
        // val courses = coursesFromATutor(id)
        for (course in courses) {
            students.addAll(course.students)
        }
        return students
    }

    @Transactional
    fun addAStudentToACourse(student: Student, course: Course)/*:Student*/ {
        student.course = course
        /*val studentRegistered=*/ studentRepository.save(student)
        // course.students.add(student)
        //courseRepository.save(course)
        //return studentRegistered
    }

    @Transactional
    fun removeAStudentFromACourse(student: Student, course: Course) {
        course.students.remove(student)
        courseRepository.save(course)
    }

    @Transactional
    fun moveAStudentIntoAnotherCourse(id: Long, id_course: Int)/*:Student*/ {
        val courseMoved = courseService.findByID(id_course)
        val student = studentService.findByID(id)
        val course = courseService.findByID(student.course!!.id!!)
        if (courseMoved.id == course.id) {
            throw UsernameExistException("Do not can moved a student to the same course:  ${course.id}")
        }
        /*val studentRegistered = */addAStudentToACourse(student, courseMoved)
        removeAStudentFromACourse(student, student.course!!)
        /*return studentRegistered*/
    }

    @Transactional
    fun absentMessageFromTutor(notifyer: Notifyer): AbsentMessageDataDTO {
//        val notifyer = senderService.getNotifyer()
        var subjectEmail = notifyer.getSubjectEmail()
        var bodyEmail = notifyer.getTextEmailForEdit()

        return AbsentMessageDataDTO(subjectEmail, bodyEmail)
    }

    @Transactional
    fun updateAbsentMessageFromTutor(updatedMessageDataDTO: AbsentMessageDataDTO, notifyer: Notifyer): AbsentMessageDataDTO {
        senderService.changeSubjectText(updatedMessageDataDTO.subject, notifyer)
        senderService.changeBodyText(updatedMessageDataDTO.body, notifyer)
        return this.absentMessageFromTutor(notifyer)
    }

    @Transactional
    fun getAllSurveys(): List<Survey> {
        return surveyService.findAll()
    }

    @Transactional
    fun removeAnStudentFromNotification(tutorId: Int, studentId: Int) {
        val notifyer = this.findByID(tutorId).notifyer
        senderService.removeStudentFromNotify(notifyer!!, studentId)
    }

    @Transactional
    fun studentsToNotifyFromTutor(tutorId: Int): List<StudentDTO> {
        val notifyer = this.findByID(tutorId).notifyer
        return senderService.studentsToNotify(notifyer!!)
    }


    fun percentageByDefault():Double{
        return  courseService.percentageByDefault()
    }
}