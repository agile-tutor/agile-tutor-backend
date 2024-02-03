package ar.edu.unq.agiletutor.webservice

import ar.edu.unq.agilemeeting.service.CourseDTO
import ar.edu.unq.agilemeeting.service.CourseRegisterDTO
import ar.edu.unq.agilemeeting.service.TutorDTO
import ar.edu.unq.agiletutor.service.*
import ar.edu.unq.agiletutor.webservice.utils.UnifiedResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@EnableAutoConfiguration
@CrossOrigin("*")
class CourseRestService {

    @Autowired
    private lateinit var tutorService: TutorService

    @Autowired
    private lateinit var courseService: CourseService
    private val builder: ResponseEntity.BodyBuilder? = null
    private val unifiedResponse = UnifiedResponseMessage()


    /**register a course*/
    @PostMapping("/api/course/register")
    fun register(@RequestBody coursedata: CourseRegisterDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutor = tutorService.findByID(coursedata.tutorId)
            val courseview = CourseDTO.desdeModelo(courseService.register(coursedata.aModelo(tutor)))
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body(courseview)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, e.localizedMessage)
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
    fun courserById(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courseView = CourseDTO.desdeModelo(courseService.findByID(id))
            response = unifiedResponse.unifiedOkResponse(courseView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /**get course by name**/
    @GetMapping("/api/course/{name}")
    fun courserByName(@PathVariable("name") name: String): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courseView = CourseDTO.desdeModelo(courseService.findByName(name))
            response = unifiedResponse.unifiedOkResponse(courseView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with name $name not found")

        }
        return response!!
    }

    /**get the tutor from a course*/
    @GetMapping("/api/course/tutor/{id}")
    fun tutorFromACourse(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val tutorView = TutorDTO.desdeModelo(courseService.tutorFromACourse(id))
            response = unifiedResponse.unifiedOkResponse(tutorView)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /** Update a course*/
    @PutMapping("/api/course/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody entity: CourseDTO): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val courseview = courseService.update(id, entity)
            response = unifiedResponse.unifiedOkResponse(courseview)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /**Students From a Course*/
    @GetMapping("/api/course/students/{id}")
    fun studentsFromACourse(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = courseService.studentsFromACourse(id).map { StudentView.desdeModelo(it) }
            response = unifiedResponse.unifiedOkResponse(students)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /**update  students attendances from a course*/
//    @PuMapping("/api/students/attendances/update/{id}/{day}")
    @PutMapping("/api/course/students/attendances/update/{id}")
    fun updateStudentsAttendancesFromACourse(
            @PathVariable("id") id: Long,
            @RequestBody attendances: List<StudentAttendanceDTO>
    ): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            println("id desde api: $id")
            courseService.updateStudentsAttendancesFromACourse(id, attendances)
            ResponseEntity.status(201)
            response = ResponseEntity.ok().body("students attendances Updated Ok")
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /** Average of Attendances  from a Course*/
    @GetMapping("/api/course/students/attendances/average/{id}")
    fun averageAttendancesFromACouurse(@PathVariable("id") id: Long): ResponseEntity<*> {
        val averageAttendances = courseService.averageAttendancesFromACourse(id)
        return ResponseEntity.ok().body(averageAttendances)
    }

    /**  Marked Down attendances at a particular day*/
    @GetMapping("/api/course/attendances/{id}")
    fun markedDownAttendanceAFromACourseATaParticularDay(@PathVariable("id") id: Long, @PathVariable("day") day: Int): ResponseEntity<*> {
        val narkedDown = courseService.markedDownAttendanceAFromACourseATaParticularDay(id, day)
        return ResponseEntity.ok().body(narkedDown)
    }

    /**Students approved From a Course*/
    @GetMapping("/api/course/students/approved/{id}")
    fun studentsApprovedFromACourse(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = courseService.studentsApprovedFromACourse(id).map { StudentView.desdeModelo(it) }
            response = unifiedResponse.unifiedOkResponse(students)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /**Students fill the survey From a Course*/
    @GetMapping("/api/course/students/survey/{id}")
    fun studentsFillSurveyFromACourse(@PathVariable("id") id: Long): ResponseEntity<*> {
        var response: ResponseEntity<*>?
        try {
            val students = courseService.studentsFillSurveydFromACourse(id).map { StudentView.desdeModelo(it) }
            response = unifiedResponse.unifiedOkResponse(students)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $id not found")
        }
        return response!!
    }

    /** Course attended at days*/
    @GetMapping("/api/course/attended/{course}")
    fun courseAttendedAtDay(@PathVariable("course") course_id: Long)
            : ResponseEntity<*> {
        var response: ResponseEntity<*>?

        try {
            val result = courseService.attendedAtDay(course_id)
            response = unifiedResponse.unifiedOkResponse(result)
        } catch (e: Exception) {
            response = unifiedResponse.unifiedNotFoundResponse(e, "Course with id $course_id not found")
        }
        return response!!
    }
}