package ar.edu.unq.agiletutor.webservice


import ar.edu.unq.agiletutor.service.TutorDTO
import ar.edu.unq.agiletutor.service.TutorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.HashMap

@Autowired
private  lateinit var  tutorService: TutorService
private val builder: ResponseEntity.BodyBuilder? = null

/*
/**register a tutor*/
@PostMapping("/api/tutors/register")
fun registerTutor (@RequestBody tutordata : TutortDTO): TutorDTO {
    return TutorDTO.desdeModelo(tutorService.register(tutordata.aModelo()))
}
*/

/**register a tutor*/
@PostMapping("/api/tutors/register")
fun register (@RequestBody tutordata :TutorDTO): ResponseEntity<*> {
    var response : ResponseEntity<*>?

    try {

        val  tutorview = TutorDTO.desdeModelo(tutorService.register(tutordata.aModelo()))
        ResponseEntity.status(201)
        response =  ResponseEntity.ok().body(tutorview)
    } catch (e: Exception) {
        ResponseEntity.status(404)

        val resultado: MutableMap<String, String> = HashMap()
        resultado["email of tutor already exits"] = tutordata.email.toString()
        response = ResponseEntity.ok().body<Map<String, String>>(resultado)
    }
    return response!!
}



@GetMapping("/api/tutors")
fun allTutors(): ResponseEntity<*> {
    val tutors = tutorService.findAll().map{ TutorDTO.desdeModelo(it)}

    return ResponseEntity.ok().body(tutors)
}







