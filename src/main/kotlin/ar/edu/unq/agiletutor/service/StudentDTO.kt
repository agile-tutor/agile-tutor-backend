package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Attendance

data class StudentDTO(
    var id: Long?,
    var name: String?,
    var surname: String?,
    var identifier: String?,
    var email: String?,
    var attendances: List<AttendanceDTO>?,
    var attendancepercentage: Double?,
    var observations: String?,
    var blocked: Boolean
) {

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
                student.blocked
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
        return student
    }
}


data class AttendanceDTO(
    var id: Int?,
    var day: Int?,
    var attended: String

) {
    companion object {
        fun desdeModelo(asistencia: Attendance): AttendanceDTO {
            return AttendanceDTO(asistencia.id, asistencia.day, asistencia.attended.toString())
        }
    }

    fun aModelo(): Attendance {
        val attendance = Attendance()
        attendance.id = id
        attendance.day = day
        attendance.attended = attended.toBoolean() // (attended=="true")
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