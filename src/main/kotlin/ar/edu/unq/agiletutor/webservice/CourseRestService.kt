package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap

@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class CourseRestService {

    @Autowired
    private lateinit var tutorService: TutorService

    @Autowired
    private lateinit var courseService: CourseService
    private val builder: ResponseEntity.BodyBuilder? = null

    /**register a course*/
    @PostMapping("/api/course/register")
    fun register(@RequestBody coursedata: CourseRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val tutor = tutorService.findByID(coursedata.tutorId)
            val courseview = CourseDTO.desdeModelo(courseService.register(coursedata.aModelo(tutor)))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(courseview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            //resultado["name of course already exist"] = coursedata.name.toString()
            resultado["Exception"] = e.localizedMessage
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get all courses**/
    @GetMapping("/api/course")
    fun allCourses(): ResponseEntity<*> {
        val courses = courseService.findAll().map { CourseDTO.desdeModelo(it) }
        return ResponseEntity.ok().body(courses)
    }

    /**get course by id**/
    @GetMapping("/api/course/{id}")
    fun courserById(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val courseView = CourseDTO.desdeModelo(courseService.findByID(id))
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(courseView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["course with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get course by name**/
    @GetMapping("/api/course/{name}")
    fun courserById(@PathVariable("name") name: String): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val courseView = CourseDTO.desdeModelo(courseService.findByName(name))
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(courseView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["course with name not found"] = name
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get the tutor from a course*/
    @GetMapping("/api/course/tutor/{id}")
    fun tutorFromACourse(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val tutorView = TutorDTO.desdeModelo(courseService.tutorFromACourse(id))
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(tutorView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["course with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }


    /** Update a course*/
    @PutMapping("/api/course/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody entity: CourseDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courseview = courseService.update(id, entity)

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(courseview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["usuario con id no encontrado"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**Students From a Course*/
    @GetMapping("/api/course/students/{id}")
    fun studentsFromACourse(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
        val courses = courseService.studentsFromACourse(id).map { StudentDTO.desdeModelo(it) }
        ResponseEntity.status(200)
        response = ResponseEntity.ok().body(courses)
    } catch (e: Exception) {
        ResponseEntity.status(404)

        val resultado: MutableMap<String, String> = HashMap()
        resultado["Not found Course with id"] = id.toString()

        response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
    }
    return response!!

}



    /**update  students attendances from a course*/
//    @PuMapping("/api/students/attendances/update/{id}/{day}")
    @PutMapping("/api/course/students/attendances/update/{id}")
    fun updateStudentsAttendancesFromACourse(
        @PathVariable("id") id: Int,
        @RequestBody attendances: List<StudentAttendanceDTO>
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            println("id desde api: $id")
            courseService.updateStudentsAttendancesFromACourse(id, attendances)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body("students attendances Updated Ok")
            } catch (e: Exception) {
                ResponseEntity.status(404)
    
                val resultado: MutableMap<String, String> = HashMap()
                resultado["Course with Id not found"] = id.toString()
                response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
            }
        return response!!
    }


    /** Average of Attendances  from a Course*/
    @GetMapping("/api/course/students/attendances/average/{id}")
    fun averageAttendancesFromACouurse( @PathVariable("id") id: Int): ResponseEntity<*> {

        val averageAttendances = courseService.averageAttendancesFromACourse(id)

        return ResponseEntity.ok().body(averageAttendances)
    }





}