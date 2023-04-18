/*
package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agiletutor.service.AttendanceDTO
import ar.edu.unq.agiletutor.service.StudentDTO
import ar.edu.unq.agiletutor.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap



@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class AttendanceRestService {

    @Autowired
    private  lateinit var  studentService: StudentService
    private val builder: ResponseEntity.BodyBuilder? = null


    /**update attendances for a student */
    @PostMapping("/api/attendances/update")
    fun updateAttendancesForAStudent (@PathVariable("id") id: Int, @RequestBody attendances : List <AttendanceDTO>): ResponseEntity<*> {
        var response : ResponseEntity<*>?

        try {

            val  studentview = StudentDTO.desdeModelo(studentService.updateattendances(id,attendances))
            ResponseEntity.status(201)
            response =  ResponseEntity.ok().body(studentview)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["Student with Id:   not found"] = id.toString()
            response = ResponseEntity.ok().body<Map<String, String>>(resultado)
        }
        return response!!
    }

}

 */