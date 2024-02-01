package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.*
import ar.edu.unq.agiletutor.service.*
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
    private val builder: ResponseEntity.BodyBuilder? = null

    /**register a tutor*/
    @PostMapping("/api/tutor/register")
    fun register(@RequestBody tutordata: TutorRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {

            val tutorview = TutorDTO.desdeModelo(tutorService.register(tutordata.aModelo()))

            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(tutorview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["email of tutor already exist"] = tutordata.email.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
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

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(tutorview)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["tutor not found"] = tutor.email
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get tutor by id**/
    @GetMapping("/api/tutor/{id}")
    fun tutorById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorView = TutorDTO.desdeModelo(tutorService.findByID(id))

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(tutorView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["tutor with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get tutor by email**/
    @GetMapping("/api/tutor/email/{email}")
    fun tutorByEmail(@PathVariable("email") email: String): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorView = TutorDTO.desdeModelo(tutorService.findByEmail(email))

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(tutorView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["tutor with email not found"] = email
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Update*/
    @PutMapping("/api/tutor/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody entity: TutorRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val userview = tutorService.update(id, entity)

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(userview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            //resultado["Not found Tutor with id"] = id.toString()
            resultado["Exception"] = e.message.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
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
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["tutor with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**Courses From a Tutor*/
    @GetMapping("/api/tutor/courses/{id}")
    fun coursesFromATutor(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courses = tutorService.coursesFromATutor(id).map { CourseDTO.desdeModelo(it) }
            ResponseEntity.status(200)

            response = ResponseEntity.ok().body(courses)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Not found Tutor with id"] = id.toString()

            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**Students From a Tutor*/
    @GetMapping("/api/tutor/students/{id}")
    fun studentsFromATutor(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = tutorService.studentsFromATutor(id).map { StudentDTO.desdeModelo(it) }
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(students)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Not found Tutor with id"] = id.toString()

            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Move a student to Another Course*/
    @PutMapping("/api/tutor/students/move/{id}/{id_course}")
    fun moveAStudentIntoAnotherCourse(
            @PathVariable("id") id: Int, @PathVariable("id_course") id_course: Long
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val student = tutorService.moveAStudentIntoAnotherCourse(id.toLong(), id_course)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(student)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Exception"] = e.message.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
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
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(message)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val result: MutableMap<String, String> = HashMap()
            result["Not found Absent Message"] = "Error"

            response = ResponseEntity.badRequest().body<Map<String, String>>(result)
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
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(message)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val result: MutableMap<String, String> = HashMap()
            result["Not found Absent Message"] = "Error"

            response = ResponseEntity.badRequest().body<Map<String, String>>(result)
        }
        return response!!
    }

    /**Students To Notify From a Tutor*/
    @GetMapping("/api/tutor/toNotify/{tutorId}")
    fun studentsToNotify(@PathVariable("tutorId") tutorId: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = tutorService.studentsToNotifyFromTutor(tutorId)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(students)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Students with tutor: not found"] = tutorId.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**remove an absent to notify*/
    @PostMapping("/api/tutor/absent/{tutorId}/{studentId}")
    fun login(@PathVariable("tutorId") tutorId: Long, @PathVariable("studentId") studentId: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            tutorService.removeAnStudentFromNotification(tutorId, studentId)
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body { }
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["an error was ocurred"] = ""
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**All Surveys*/
    @GetMapping("/api/tutor/survey")
    fun allSurveys(): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val surveys = tutorService.getAllSurveys()
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(surveys)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Not found"] = "Check Error"

            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }


    /** Get the percentage by Default */
    @GetMapping("/api/tutor/pecentagebydefault")
    fun percentageByDefault(): ResponseEntity<*> {
        val percentageByDefault= tutorService.percentageByDefault()

        return ResponseEntity.ok().body(percentageByDefault)
    }

    /** Update Percentage by Default */
    @PostMapping("/api/tutor/pecentagebydefault/{percentage}")
    fun updatePercentageByDefault(@PathVariable("percentage") percentage: Int): ResponseEntity<*> {
      val  percentage = tutorService.updatePercentageByDefault(percentage.toDouble())
        return ResponseEntity.ok().body(percentage)
    }
}