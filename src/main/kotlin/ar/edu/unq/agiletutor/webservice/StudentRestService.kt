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
            val studentView = StudentDTO.desdeModelo (studentService.findByID(id.toLong()))
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
    @GetMapping("/api/students/{name}")
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
    @PutMapping("/api/students/{id}")
    fun update (@PathVariable("id") id: Int, @RequestBody entity: StudentDTO): ResponseEntity<*> {
        var response : ResponseEntity<*>?
        try {
            val userview = studentService.update(id.toLong(),entity)

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

    /**update attendances for a student */
    @PostMapping("/api/students/attendances/update/{id}")
    fun updateAttendancesForAStudent (@PathVariable("id") id: Int, @RequestBody attendances : List <AttendanceDTO>): ResponseEntity<*> {
        var response : ResponseEntity<*>?

        try {
            val  studentview = StudentDTO.desdeModelo(studentService.updateattendances(id.toLong(),attendances))
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


    /**Attendances  From a Student*/
    @GetMapping("/api/students/attendances/{id}")
    fun attendancesFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val attendances = studentService.attendancesFromAStudent(id.toLong()).map { AttendanceDTO.desdeModelo(it) }

        return ResponseEntity.ok().body(attendances)

    }


    /** Percentage of Attendances  From a Student*/
    @GetMapping("/api/students/attendances/{id}")
    fun percentageOfAttendancesFromAStudent(@PathVariable("id") id: Int): ResponseEntity<*> {

        val  percentageOfAttendances = studentService.attendancesPercentageFromAStudent(id.toLong())

        return ResponseEntity.ok().body(percentageOfAttendances)

    }









}