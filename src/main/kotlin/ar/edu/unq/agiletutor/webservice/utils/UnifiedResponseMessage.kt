package ar.edu.unq.agiletutor.webservice.utils

import org.springframework.http.ResponseEntity
import java.util.HashMap

class UnifiedResponseMessage {
    fun unifiedOkResponse(entity: Any): ResponseEntity<*> {
        ResponseEntity.status(200)
        return ResponseEntity.ok().body(entity)
    }

    fun unifiedNotFoundResponse(e: Exception, message: String) : ResponseEntity<*> {
        ResponseEntity.status(404)
        val resultado: MutableMap<String, String> = HashMap()
        resultado["can't find the requested resource"] = message
        return ResponseEntity.badRequest().body<Map<String, String>>(resultado)
    }
}