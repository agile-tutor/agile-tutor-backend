package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Attendance
import org.springframework.beans.factory.annotation.Autowired




data class StudentFromACourseDTO(
    var id: Long?,
    var name: String?,
    var surname: String?,
    var identifier: String?,
    var email: String?,
    var courseId: Int,

    )

{
    @Autowired
    private lateinit var courseService: CourseService

    companion object {
        fun desdeModelo(student: Student): StudentFromACourseDTO {
           return StudentFromACourseDTO(
                student.id,
                student.name,
                student.surname,
                student.identifier,
                student.email,
                student.course!!.id!!
            )
        }

    }

    fun aModelo(): Student {
        val student = Student()
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        student.attendances
        // attendances!!.map { AttendanceDTO(it.id, it.day, it.attended).aModelo() }.toMutableSet()
        student.attendancepercentage = 0.0
        student.observations = ""
        student.blocked = false
        student.course = courseService.findByID(courseId)
        return student
    }
}

data class ManyStudentsFromACourse(
      var studentsDTO : MutableSet<StudentFromACourseDTO> = HashSet()

) {
    fun aModelo(courseId: Int): List<Student> {

        return studentsDTO.map {
            StudentFromACourseDTO(
                it.id,
                it.name,
                it.surname,
                it.identifier,
                it.email,
                courseId
            ).aModelo()
        }.toMutableList()

    }

    companion object {
        fun desdeModelo(students: List <Student>): List <StudentFromACourseDTO> {
           return students.map { StudentFromACourseDTO.desdeModelo(it)}
        }
    }

}
data class StudentRegisterDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var identifier: String?,
        var email: String?,
       // var attendances: List<AttendanceDTO>?,
      //  var attendancepercentage: Double?,
        var observations: String?,
      //  var blocked: Boolean,
        var courseId: Int
) {
    @Autowired
    private lateinit var courseService: CourseService

    companion object {
        fun desdeModelo(student: Student): StudentRegisterDTO {
            val asistenciasDTO = student.attendances.map { AttendanceDTO.desdeModelo(it) }

            return StudentRegisterDTO(
                    student.id,
                    student.name,
                    student.surname,
                    student.identifier,
                    student.email,
                  //  asistenciasDTO,
                  //  student.attendancepercentage,
                   student.observations,
                   // student.blocked,
                    student.course!!.id!!
            )
        }
    }

    fun aModelo(): Student {
        val student = Student()
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        student.attendances
       // attendances!!.map { AttendanceDTO(it.id, it.day, it.attended).aModelo() }.toMutableSet()
        student.attendancepercentage = 0.0
        student.observations = ""
       student.blocked = false
        student.course = courseService.findByID(courseId)
        return student
    }
}



data class StudentDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var identifier: String?,
        var email: String?,
        var attendances: List<AttendanceDTO>?,
        var attendancepercentage: Double?,
        var observations: String?,
        var blocked: Boolean,
        var courseId: Int

) {
    @Autowired
    private lateinit var courseService: CourseService

    companion object {
        fun desdeModelo(student: Student): StudentDTO {
            val asistenciasDTO = student.attendances.map { AttendanceDTO.desdeModelo(it) }

            return StudentDTO(
                    student.id,
                    student.name,
                    student.surname,
                    student.identifier,
                    student.email,
                    asistenciasDTO,
                    student.attendancepercentage,
                    student.observations,
                    student.blocked,
                    student.course!!.id!!

            )
        }
    }

    fun aModelo(): Student {
        val student = Student()
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        // student.attendances
        attendances!!.map { AttendanceDTO(it.id, it.day, it.attended).aModelo() }.toMutableSet()
        student.attendancepercentage = 0.0
        student.observations = ""
        student.blocked = blocked
        student.course = courseService.findByID(courseId)
        return student
    }

}

data class AttendanceDTO(
        var id: Int?,
        var day: Int?,
        var attended: Boolean

) {
    companion object {
        fun desdeModelo(asistencia: Attendance): AttendanceDTO {
            return AttendanceDTO(asistencia.id, asistencia.day, asistencia.attended)
        }
    }

    fun aModelo(): Attendance {
        val attendance = Attendance()
        attendance.id = id
        attendance.day = day
        attendance.attended = attended
        return attendance
    }
}

data class AttendanceViewDTO(
        var day: Int?
) {
    companion object {
        fun desdeModelo(asistencia: Attendance): AttendanceViewDTO {
            return AttendanceViewDTO(asistencia.day)
        }
    }
}


data class StudentAttendanceDTO(
        var studentId: Int,
        var attendance: AttendanceDTO
)

data class StudentBlockDTO(
        var blocked: Boolean
)