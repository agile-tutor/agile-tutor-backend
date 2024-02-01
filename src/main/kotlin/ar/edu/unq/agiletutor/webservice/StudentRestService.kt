package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.SurveyDataDTO
import ar.edu.unq.agiletutor.model.Survey
import ar.edu.unq.agiletutor.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap

@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class StudentRestService {

    @Autowired
    private lateinit var studentService: StudentService
    private val builder: ResponseEntity.BodyBuilder? = null

    @Autowired
    private lateinit var courseService: CourseService

    /**register a student*/
    @PostMapping("/api/students/register")
    fun register(@RequestBody studentdata: StudentRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val course = courseService.findByID(studentdata.courseId)
            val userview = StudentRegisterDTO.desdeModelo(studentService.register(studentdata.aModelo(course)))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(userview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["email of user already exits"] = studentdata.email.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    @GetMapping("/api/students")
    fun allStudents(): ResponseEntity<*> {
        val students = studentService.findAll().map { StudentDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(students)
    }

    /**get student by id**/
    @GetMapping("/api/students/{id}")
    fun studentById(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val studentView = StudentDTO.desdeModelo(studentService.findByID(id.toLong()))
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(studentView)

        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["student with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get tutor by email**/
    @GetMapping("/api/students/name/{name}")
    fun studentsByName(@PathVariable("name") name: String): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val studentsView = studentService.findByName(name).map { StudentDTO.desdeModelo(it) }

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(studentsView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["student with name not found"] = name
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Update*/
    @PutMapping("/api/students/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody entity: StudentDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val student = studentService.update(id.toLong(), entity)

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(student)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["student with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**update attendances for a student */
    @PostMapping("/api/students/attendances/update/{id}")
    fun updateAttendancesForAStudent(
            @PathVariable("id") id: Int,
            @RequestBody attendances: List<AttendanceDTO>
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val studentview = StudentDTO.desdeModelo(studentService.updateattendances(id.toLong(), attendances))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(studentview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Student with Id:   not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }


    /**Attendances  From a Student*/
    @GetMapping("/api/students/attendances/{id}")
    fun attendancesFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val attendances = studentService.attendancesFromAStudent(id.toLong()).map { AttendanceDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(attendances)
    }

    /** Percentage of Attendances  From a Student*/
    @GetMapping("/api/students/attendances/percentage/{id}")
    fun percentageOfAttendancesFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val percentageOfAttendances = studentService.attendancesPercentageFromAStudent(id.toLong())

        return ResponseEntity.ok().body(percentageOfAttendances)
    }


    /** Average of Attendances  From all Students*/
    @GetMapping("/api/students/attendances/average")
    fun averageAttendancesFromAllStudents(): ResponseEntity<*> {

        val averageAttendances = studentService.averageAttendancesFromAllStudents()

        return ResponseEntity.ok().body(averageAttendances)
    }

    /** students without absents */
    @GetMapping("/api/students/attendances/attended")
    fun studentsWithoutAbsents(): ResponseEntity<*> {

        val studentsWithoutAbsents = studentService.studentsWirhoutAbsents().map { StudentDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(studentsWithoutAbsents)
    }

    /** students with absents */
    @GetMapping("/api/students/attendances/absent")
    fun studentsWithAbsents(): ResponseEntity<*> {

        val studentsWithAbsents = studentService.studentsWithAbsents().map { StudentDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(studentsWithAbsents)
    }

    /** attended days from a student */
    @GetMapping("/api/students/attendances/attended/days/{id}")
    fun attendedDaysFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val attendedDays = studentService.attendedDays(id.toLong()).map { AttendanceViewDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(attendedDays)
    }

    /** absent days from a student */
    @GetMapping("/api/students/attendances/absents/days/{id}")
    fun absentDaysFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val absentDays = studentService.absentdDays(id.toLong()).map { AttendanceViewDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(absentDays)
    }

    /** students attended at a particular day  */
    @GetMapping("/api/students/attendances/attended/{day}")
    fun studentsAttendedAtAParticularDay(@PathVariable("day") day: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val studentsAttendedAtAParticularDay =
                    studentService.studentsAttendedAtAParticularDay(day).map { StudentDTO.desdeModelo(it) }
            response = ResponseEntity.ok().body(studentsAttendedAtAParticularDay)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Exception"] = e.message.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** students absent at a particular day  */
    @GetMapping("/api/students/attendances/absent/{day}")
    fun studentsAbsentAtAParticularDay(@PathVariable("day") day: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val studentsAttendedAtAParticularDay =
                    studentService.studentsAbsentAtAParticularDay(day).map { StudentDTO.desdeModelo(it) }

            response = ResponseEntity.ok().body(studentsAttendedAtAParticularDay)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Exception"] = e.message.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Block or unblock a student */
    @PutMapping("/api/students/block/{id}")
    fun blockOrUnBlockAStudent(@PathVariable("id") id: Int, @RequestBody blocked: StudentBlockDTO): ResponseEntity<*> {

        val student = studentService.blockOrUnblockAStudent(id.toLong(), blocked.blocked)

        return ResponseEntity.ok().body(student)
    }

    /**check mail **/
    @GetMapping("/api/students/checkmail/{email}")
    fun checkMail(@PathVariable("email") email: String): ResponseEntity<*> {
        val checked = studentService.checkMail(email)
        return ResponseEntity.ok().body(checked)
    }

    /**post survey for a student */
    @PostMapping("/api/students/survey/{email}/")
    fun studentSurveyResponse(
            @PathVariable("email") email: String,
            @RequestBody survey: SurveyDataDTO
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            println("apiSurvey" + email + survey)
            val student = studentService.findByEmail(email)[0]
            val surveyResponse = studentService.saveStudentSurvey(student.id!!, survey)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(surveyResponse)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Student with email: not found"] = email
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** register Many students */
    @PostMapping("/api/students/many/register/{id}")
    fun registerMany(@PathVariable("id") id: Long, @RequestBody studentdata: MutableList<StudentFromACourseDTO>): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val course = courseService.findByID(id)
            val studentsview =
                    studentService.registerMany(studentdata.map
                    {
                        StudentFromACourseDTO(
                                it.id,
                                it.name,
                                it.surname,
                                it.identifier,
                                it.email,
                                id
                        ).aModelo(course)
                    }.toMutableList()).map { StudentFromACourseDTO.desdeModelo(it) }
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(studentsview)


        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["email of user already exits"] = e.message.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!

    }
}

