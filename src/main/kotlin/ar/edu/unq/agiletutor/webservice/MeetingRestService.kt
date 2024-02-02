package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.*
import ar.edu.unq.agiletutor.service.MeetingService
import ar.edu.unq.agiletutor.webservice.utils.UnifiedResponseMessage
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
    private val unifiedResponse = UnifiedResponseMessage()

    /**register meeting*/
    @PostMapping("/api/meeting/register")
    fun register(@RequestBody meetingdata: MeetingRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val meetingView = MeetingView.desdeModelo(meetingService.register(meetingdata))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(meetingView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, e.localizedMessage)
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
            response = unifiedResponse.unifiedOkResponse(meetingView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Meeting with id $id not found")
        }
        return response!!
    }

    /** Update a meeting*/
    @PutMapping("/api/meeting/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody entity: MeetingRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val meeting = meetingService.update(id, entity)
            response = unifiedResponse.unifiedOkResponse(meeting)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Meeting with id $id not found")
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
            response = unifiedResponse.unifiedNotFoundResponse(e, "Meeting with id $id not found")
        }
        return response!!
    }
}