package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.*
import ar.edu.unq.agiletutor.service.*
import ar.edu.unq.agiletutor.webservice.utils.UnifiedResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap

@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class TutorRestService {

    @Autowired
    private lateinit var tutorService: TutorService
    private val unifiedResponse = UnifiedResponseMessage()

    /**register a tutor*/
    @PostMapping("/api/tutor/register")
    fun register(@RequestBody tutordata: TutorRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val tutorview = TutorDTO.desdeModelo(tutorService.register(tutordata.aModelo()))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(tutorview)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Email of tutor "+ tutordata.email.toString() +" already exist")
        }
        return response!!
    }

    @GetMapping("/api/tutor")
    fun allTutors(): ResponseEntity<*> {
        val tutors = tutorService.findAll().map { TutorDTO.desdeModelo(it) }
        return ResponseEntity.ok().body(tutors)
    }

    /**login a tutor*/
    @PostMapping("/api/tutor/login")
    fun login(@RequestBody tutor: TutorLoginDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorview = TutorDTO.desdeModelo(tutorService.login(tutor.email, tutor.password))
            response = unifiedResponse.unifiedOkResponse(tutorview)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with email " + tutor.email + " not found")
        }
        return response!!
    }

    /**get tutor by id**/
    @GetMapping("/api/tutor/{id}")
    fun tutorById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorView = TutorDTO.desdeModelo(tutorService.findByID(id))
            response = unifiedResponse.unifiedOkResponse(tutorView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with id $id not found")
        }
        return response!!
    }

    /**get tutor by email**/
    @GetMapping("/api/tutor/email/{email}")
    fun tutorByEmail(@PathVariable("email") email: String): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorView = TutorDTO.desdeModelo(tutorService.findByEmail(email))
            response = unifiedResponse.unifiedOkResponse(tutorView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with email $email not found")
        }
        return response!!
    }

    /** Update*/
    @PutMapping("/api/tutor/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody entity: TutorRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val userview = tutorService.update(id, entity)
            response = unifiedResponse.unifiedOkResponse(userview)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            response = unifiedResponse.unifiedNotFoundResponse(e, e.message.toString())
        }
        return response!!
    }

    /**Delete tutor by id*/
    @DeleteMapping("/api/tutor/{id}")
    fun deleteTutorById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            tutorService.deleteById(id)
            val resultado: MutableMap<String, Long> = HashMap()
            resultado["succesfully tutor deleted with iD"] = id
            response = ResponseEntity.ok().body<Map<String, Long>>(resultado)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with id $id not found")
        }
        return response!!
    }

    /**Courses From a Tutor*/
    @GetMapping("/api/tutor/courses/{id}")
    fun coursesFromATutor(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courses = tutorService.coursesFromATutor(id).map { CourseDTO.desdeModelo(it) }
            response = unifiedResponse.unifiedOkResponse(courses)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with id $id not found")
        }
        return response!!
    }

    /**Students From a Tutor*/
    @GetMapping("/api/tutor/students/{id}")
    fun studentsFromATutor(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = tutorService.studentsFromATutor(id).map { StudentView.desdeModelo(it) }
            response = unifiedResponse.unifiedOkResponse(students)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Tutor with id $id not found")
        }
        return response!!
    }

    /** Move a student to Another Course*/
    @PutMapping("/api/tutor/students/move/{id}/{id_course}")
    fun moveAStudentIntoAnotherCourse(
            @PathVariable("id") id: Int, @PathVariable("id_course") courseId: Long
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val student = StudentView.desdeModelo(tutorService.moveAStudentIntoAnotherCourse(id.toLong(), courseId))
            response = unifiedResponse.unifiedOkResponse(student)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            response = unifiedResponse.unifiedNotFoundResponse(e, e.message.toString())
        }
        return response!!
    }

    /** Move a Course to Another Tutor **/
        @PutMapping("/api/tutor/course/move/{tutorId}/{courseId}")
    fun changeTutorFromACourse(
            @PathVariable("tutorId") tutorId: Long, @PathVariable("courseId") courseId: Long
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val course = CourseDTO.desdeModelo(tutorService.changeTutorFromACourse(tutorId, courseId))
            response = unifiedResponse.unifiedOkResponse(course)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            response = unifiedResponse.unifiedNotFoundResponse(e, e.message.toString())
        }
        return response!!
    }

    /** Absent Message From a Tutor*/
    @GetMapping("/api/tutor/absentmessage/{tutorId}")
    fun absentMessageFromTutor(@PathVariable("tutorId") tutorId: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val notifyer = tutorService.findByID(tutorId).notifyer
            val message = tutorService.absentMessageFromTutor(notifyer!!)
            response = unifiedResponse.unifiedOkResponse(message)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Absent Message not found")
        }
        return response!!
    }

    /** Modify Absent Message From a Tutor*/
    @PutMapping("/api/tutor/absentmessage/{tutorId}")
    fun updateAbsentMessageFromTutor(@PathVariable("tutorId") tutorId: Long, @RequestBody absentMessageDataDTO: AbsentMessageDataDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val notifyer = tutorService.findByID(tutorId).notifyer
            val message = tutorService.updateAbsentMessageFromTutor(absentMessageDataDTO, notifyer!!)
            response = unifiedResponse.unifiedOkResponse(message)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Absent Message not found")
        }
        return response!!
    }

    /**Students To Notify From a Tutor*/
    @GetMapping("/api/tutor/toNotify/{tutorId}")
    fun studentsToNotify(@PathVariable("tutorId") tutorId: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = tutorService.studentsToNotifyFromTutor(tutorId)
            response = unifiedResponse.unifiedOkResponse(students)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Students from tutorId $tutorId not found")
        }
        return response!!
    }

    /**remove an absent to notify*/
    @PostMapping("/api/tutor/absent/{tutorId}/{studentId}")
    fun login(@PathVariable("tutorId") tutorId: Long, @PathVariable("studentId") studentId: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            tutorService.removeAnStudentFromNotification(tutorId, studentId)
            response = unifiedResponse.unifiedOkResponse("")
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Check requested resource")
        }
        return response!!
    }

    /**All Surveys*/
    @GetMapping("/api/tutor/survey")
    fun allSurveys(): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val surveys = tutorService.getAllSurveys()
            response = unifiedResponse.unifiedOkResponse(surveys)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Check requested resource")
        }
        return response!!
    }


    /** Get the percentage by Default */
    @GetMapping("/api/tutor/pecentagebydefault")
    fun percentageByDefault(): ResponseEntity<*> {
        val percentageByDefault = tutorService.percentageByDefault()
        return ResponseEntity.ok().body(percentageByDefault)
    }

    /** Update Percentage by Default */
    @PostMapping("/api/tutor/pecentagebydefault/{percentage}")
    fun updatePercentageByDefault(@PathVariable("percentage") percentage: Int): ResponseEntity<*> {
        val savedPercentage = tutorService.updatePercentageByDefault(percentage.toDouble())
        return ResponseEntity.ok().body(savedPercentage)
    }
}