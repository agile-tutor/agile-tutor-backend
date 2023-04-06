package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agiletutor.StudentRegisterMapper
import ar.edu.unq.agiletutor.StudentViewMapper
import ar.edu.unq.agiletutor.service.StudentDTO
import ar.edu.unq.agiletutor.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap


@RestController
@EnableAutoConfiguration
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







}