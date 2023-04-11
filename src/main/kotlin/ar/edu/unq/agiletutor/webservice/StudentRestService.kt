package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agiletutor.StudentRegisterMapper
import ar.edu.unq.agiletutor.StudentViewMapper
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
    private  lateinit var  studentService: StudentService
    private val builder: ResponseEntity.BodyBuilder? = null




    /**register a student*/
    @PostMapping("/api/students/register")
    fun register (@RequestBody studentdata :StudentDTO): ResponseEntity<*> {
        var response : ResponseEntity<*>?

        try {

            val  userview = StudentDTO.desdeModelo(studentService.register(studentdata.aModelo()))
            ResponseEntity.status(201)
            response =  ResponseEntity.ok().body(userview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["email of user already exits"] = studentdata.email.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response!!
    }



    @GetMapping("/api/students")
    fun allStudents(): ResponseEntity<*> {
        val students = studentService.findAll().map{StudentDTO.desdeModelo(it)}

        return ResponseEntity.ok().body(students)
    }

    /**get student by id**/
    @GetMapping("/api/students/{id}")
    fun studentById(@PathVariable("id") id: Int): ResponseEntity<*> {
        var response : ResponseEntity<*>?
        try {
            val studentView = StudentDTO.desdeModelo (studentService.findByID(id))

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(studentView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["student with id not found"] = id.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response !!
    }

    /**get tutor by email**/
    @GetMapping("/api/tutors/{email}")
    fun studentsByName (@PathVariable("name") name: String): ResponseEntity<*> {
        var response : ResponseEntity<*>?
        try {
            val studentsView = studentService.findByName(name).map { StudentDTO.desdeModelo(it) }


            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(studentsView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["student with name not found"] = name
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response !!
    }



    /** Update*/
    @PutMapping("/api/tutors/{id}")
    fun update (@PathVariable("id") id: Int, @RequestBody entity: StudentDTO): ResponseEntity<*> {
        var response : ResponseEntity<*>?
        try {
            val userview = studentService.update(id,entity)

            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(userview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["usuario con id no encontrado"] = id.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response !!
    }









}