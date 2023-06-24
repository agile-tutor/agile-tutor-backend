package ar.edu.unq.agiletutor.webservice

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
    fun tutorById(@PathVariable("id") id: Int): ResponseEntity<*> {
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
    fun update(@PathVariable("id") id: Int, @RequestBody entity: TutorRegisterDTO): ResponseEntity<*> {
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
    fun deleteTutorById(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            tutorService.deleteById(id)
            val resultado: MutableMap<String, Int> = HashMap()
            resultado["succesfully tutor deleted with iD"] = id
            response = ResponseEntity.ok().body<Map<String, Int>>(resultado)

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
    fun coursesFromATutor(@PathVariable("id") id: Int): ResponseEntity<*> {
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
    fun studentsFromATutor(@PathVariable("id") id: Int): ResponseEntity<*> {
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
            @PathVariable("id") id: Int, @PathVariable("id_course") id_course: Int
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
    @GetMapping("/api/tutor/absentmessage")
    fun absentMessageFromTutor(): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val message = tutorService.absentMessageFromTutor()
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
    @PutMapping("/api/tutor/absentmessage")
    fun updateAbsentMessageFromTutor(@RequestBody absentMessageDataDTO: AbsentMessageDataDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val message = tutorService.updateAbsentMessageFromTutor(absentMessageDataDTO)
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
}