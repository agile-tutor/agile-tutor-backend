package ar.edu.unq.agiletutor.service
import ar.edu.unq.agiletutor.model.Alumno
import ar.edu.unq.agiletutor.model.Asistencia

data class StudentDTO(
    var id: Long?,
    var name: String?,
    var surname: String?,
    var identifier: String?,
    var email: String?,
    var attendances: List<AttendanceDTO>?,
    var attendancepercentage: Double?,
    var observations: String?
) {

    companion object {
        fun desdeModelo(student: Alumno): StudentDTO {
            val asistenciasDTO = student.attendances.map { AttendanceDTO.desdeModelo(it) }

            return StudentDTO(
                student.id,
                student.name,
                student.surname,
                student.identifier,
                student.email,
                asistenciasDTO,
                student.attendancepercentage,
                student.observations
            )
        }
    }

    fun aModelo(): Alumno {
        val student = Alumno()
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        //student.attendances = attendances!!.map {  AttendanceDTO (it.id,it.day,it.check).aModelo() }.toMutableSet()
        student.attendances = mutableSetOf<Asistencia>()
        student.attendancepercentage = 0.0
        student.observations = ""

        return student
    }
}


data class AttendanceDTO(
    var id: Long?,
    var day: Int?,
    var attended: String?

) {
    companion object {
        fun desdeModelo(asistencia: Asistencia): AttendanceDTO {
            return AttendanceDTO(asistencia.id, asistencia.day, asistencia.attended.toString())
        }
    }

    fun aModelo(): Asistencia {
        val attendance = Asistencia()
        attendance.id = id!!.toLong()
        attendance.day = day!!.toInt()
        attendance.attended = attended.toBoolean()
        return attendance
    }
}