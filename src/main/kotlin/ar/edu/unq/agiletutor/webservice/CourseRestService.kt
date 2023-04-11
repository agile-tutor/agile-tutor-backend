package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.service.CourseService
import ar.edu.unq.agiletutor.service.StudentDTO
import ar.edu.unq.agiletutor.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap


@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class CourseRestService {

    @Autowired
    private  lateinit var  courseService: CourseService
    private val builder: ResponseEntity.BodyBuilder? = null


    /**register a course*/
    @PostMapping("/api/courses/register")
    fun register (@RequestBody coursedata : Course): ResponseEntity<*> {
        var response : ResponseEntity<*>?

        try {

            val  courseview = courseService.register(coursedata)
            ResponseEntity.status(201)
            response =  ResponseEntity.ok().body(courseview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["name of course already exits"] = coursedata.name.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response!!
    }

}