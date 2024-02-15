package ar.edu.unq.agilemeeting.service
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Meeting
import ar.edu.unq.agiletutor.model.Tutor

data class TutorLoginDTO(
        var email: String,
        var password: String
)

data class TutorRegisterDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var email: String?,
        var password: String?
) {
    fun aModelo(): Tutor {
        val tutor = Tutor()
        tutor.id = id
        tutor.name = name
        tutor.surname = surname
        tutor.email = email
        // tutor.courses = courses!!.map {  CourseDTO (it.id,it.name).aModelo() }.toMutableSet()
        tutor.password = password
        return tutor
    }
}

data class TutorDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var email: String?
        //var courses: List<CourseDTO>?,
) {

    companion object {
        fun desdeModelo(tutor: Tutor): TutorDTO {
            //  val coursesDTO = meeting.courses.map { CourseDTO.desdeModelo(it) }

            return TutorDTO(
                    tutor.id,
                    tutor.name,
                    tutor.surname,
                    tutor.email
                    //    coursesDTO
            )
        }
    }

    fun aModelo(): Tutor {
        val tutor = Tutor()
        tutor.id = id
        tutor.name = name
        tutor.surname = surname
        tutor.email = email
        // meeting.courses = courses!!.map {  CourseDTO (it.id,it.name).aModelo() }.toMutableSet()
        return tutor
    }
}

data class CourseRegisterDTO(
        var id: Long,
        var name: String?,
        var tutorId: Long
) {
    companion object {
        fun desdeModelo(course: Course): CourseDTO {
            return CourseDTO(course.id!!, course.name)
        }
    }

    fun aModelo(tutor: Tutor): Course {
        val course = Course()
        course.id = id
        course.name = name
        course.students = mutableSetOf()
        course.tutor = tutor
        println("iddelcurso" + course.id)
        return course
    }
}

data class CourseDTO(
        var id: Long,
        var name: String?,

        ) {
    companion   object {
        fun desdeModelo(course: Course): CourseDTO {
            println("iddelcurso11" + course.id)
            return CourseDTO(course.id!!, course.name)
        }
    }

    fun aModelo(): Course {
        val course = Course()
        course.id = id
        course.name = name
        return course
    }
}

data class AbsentMessageDataDTO(
        var subject: String,
        var body: String
)

data class SurveyDataDTO(
        var ciudad: String,
        var wifi: Boolean,
        var datos: Boolean,
        var pc: Boolean,
        var notebook: Boolean,
        var celular: Boolean,
        var tablet: Boolean,
        var entorno: String,
        var exclusividad: String,
        var estado: String
)

//MEETING DTO//
data class MeetingRegisterDTO(
        var id: Long,
        var title: String?,
        var date: String?,
        var day: Int
) {
    fun aModelo(): Meeting {
        val meeting = Meeting()
        meeting.id = id
        meeting.title = title
        meeting.date = date
        meeting.day = day
        return meeting
    }
}

data class MeetingView(
        var id: Long?,
        var title: String?,
        var date: String?,
        var day: Int?
) {

    companion object {
        fun desdeModelo(meeting: Meeting): MeetingView {

            return MeetingView(
                    meeting.id,
                    meeting.title,
                    meeting.date,
                    meeting.day
            )
        }
    }
}