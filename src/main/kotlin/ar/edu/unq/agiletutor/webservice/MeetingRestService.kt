package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.*
import ar.edu.unq.agiletutor.service.MeetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap

@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class MeetingRestService {

    @Autowired
    private lateinit var meetingService: MeetingService

    /**register meeting*/
    @PostMapping("/api/meeting/register")
    fun register(@RequestBody meetingdata: MeetingRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
    
        try {
            val meetingView = MeetingView.desdeModelo(meetingService.register(meetingdata))
            println("iddelmeeting" + meetingView.id)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(meetingView)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            //resultado["name of course already exist"] = coursedata.name.toString()
            resultado["Exception"] = e.localizedMessage
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**get all meetings**/
    @GetMapping("/api/meeting")
    fun allMeetings(): ResponseEntity<*> {
        val meetings = meetingService.findAll().map { MeetingView.desdeModelo(it) }
        return ResponseEntity.ok().body(meetings)
    }

    /**get meeting by id**/
    @GetMapping("/api/meeting/{id}")
    fun meetingById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val meetingView = MeetingView.desdeModelo(meetingService.findByID(id))
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(meetingView)
        } catch (e: Exception) {
            ResponseEntity.status(404)
            val resultado: MutableMap<String, String> = HashMap()
            resultado["meeting with id not found"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /** Update a meeting*/
    @PutMapping("/api/meeting/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody entity: MeetingRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val meeting = meetingService.update(id, entity)
            println("modifiedmeetingRest"+meeting.title)
            ResponseEntity.status(200)
            response = ResponseEntity.ok().body(meeting)
        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["meeting con id no encontrado"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }

    /**Delete meeting by id*/
    @DeleteMapping("/api/meeting/{id}")
    fun deleteMeetingById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            meetingService.deleteById(id)
            val resultado: MutableMap<String, Long> = HashMap()
            resultado["meeting with ID $id deleted"] = id
            response = ResponseEntity.ok().body<Map<String, Long>>(resultado)

        } catch (e: Exception) {
            ResponseEntity.status(404)

            val resultado: MutableMap<String, String> = HashMap()
            resultado["something goes wrong"] = id.toString()
            response = ResponseEntity.badRequest().body<Map<String, String>>(resultado)
        }
        return response!!
    }
}