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
    private lateinit var courseService: CourseService
    private val builder: ResponseEntity.BodyBuilder? = null

    /**register a course*/
    @PostMapping("/api/course/register")
    fun register(@RequestBody coursedata: Course): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val courseview = courseService.register(coursedata)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(courseview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["name of course already exits"] = coursedata.name.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
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
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
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
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**Students From a Course*/
    @GetMapping("/api/course/students/{id}")
    fun studentsFromACourse(@PathVariable("id") id: Int): ResponseEntity<*> {
        val courses = courseService.studentsFromACourse(id).map { StudentDTO.desdeModelo(it) }
        return ResponseEntity.ok().body(courses)
    }

    /**Students From a Tutor*/
    @GetMapping("/api/course/students/tutor/{id}")
    fun studentsFromATutor(@PathVariable("id") id: Int): ResponseEntity<*> {
        val courses = courseService.studentsFromATutor(id).map { StudentDTO.desdeModelo(it) }
        return ResponseEntity.ok().body(courses)
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
            courseService.updateStudentsAttendancesFromACourse(id, attendances)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body("students attendances Updated Ok")
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Course with Id not found"] = id.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Move a student to Another Course*/
    @PutMapping("/api/course/students/change/{id}/{id_course}")
        fun moveAStudentIntoAnotherCourse(
            @PathVariable("id") id: Int,@PathVariable("id_course") id_course: Int
        ): ResponseEntity<*> {
            var response: ResponseEntity<*>?

            try {
                val student = courseService.moveAStudentIntoAnotherCourse(id.toLong(), id_course)
                ResponseEntity.status(201)
                response = ResponseEntity.ok().body(student)

            } catch (e: Exception) {
                ResponseEntity.status(404)

                val resultado: MutableMap<String, String> = HashMap()
                resultado["Course with Id not found"] = id.toString()
                response = ResponseEntity.ok().body<Map<String, String>>(resultado)
            }
            return response!!

        }

}