package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Meeting

data class StudentFromACourseDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var identifier: String?,
        var email: String?,
        var courseId: Long,
        ) {

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

    fun aModelo(course: Course): Student {
        val student = Student()
        val attendances = mutableSetOf<Attendance>()
        for (i in (1..6)) {
            attendances.add(Attendance(i, false))
        }
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        student.attendances = attendances
        // attendances!!.map { AttendanceDTO(it.id, it.day, it.attended).aModelo() }.toMutableSet()
        student.observations = ""
        student.blocked = false
        student.course = course
        return student
    }
}

data class ManyStudentsFromACourse(
        var studentsDTO: MutableSet<StudentFromACourseDTO> = HashSet()
) {
    fun aModelo(course: Course): List<Student> {

        return studentsDTO.map {
            StudentFromACourseDTO(
                    it.id,
                    it.name,
                    it.surname,
                    it.identifier,
                    it.email,
                    course.id!!
            ).aModelo(course)
        }.toMutableList()
    }

    companion object {
        fun desdeModelo(students: List<Student>): List<StudentFromACourseDTO> {
            return students.map { StudentFromACourseDTO.desdeModelo(it) }
        }
    }
}

data class StudentRegisterDTO(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var identifier: String?,
        var email: String?,
        var observations: String?,
        var courseId: Long
) {
    fun aModelo(course: Course, meetings: List<Meeting>): Student {
        val student = Student()
        val attendances = mutableSetOf<Attendance>()
        for (m in meetings) {
            attendances.add(Attendance(m.day, false))
        }
        student.id = id
        student.name = name
        student.surname = surname
        student.identifier = identifier
        student.email = email
        student.attendances = attendances
        student.observations = observations!!
        student.blocked = false
        student.course = course
        return student
    }
}

data class StudentView(
        var id: Long?,
        var name: String?,
        var surname: String?,
        var identifier: String?,
        var email: String?,
        var attendancepercentage: Double?,
        var observations: String?,
        var blocked: Boolean,
        var courseId: Long
) {

    companion object {
        fun desdeModelo(student: Student): StudentView {
 //           val asistenciasDTO = student.attendances.map { AttendanceDTO.desdeModelo(it) }

            return StudentView(
                    student.id,
                    student.name,
                    student.surname,
                    student.identifier,
                    student.email,
                    student.attendancePercentage(),
                    student.observations,
                    student.blocked,
                    student.course!!.id!!
            )
        }
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
        var studentId: Long,
            var attendance: AttendanceDTO
)

data class StudentBlockDTO(
        var blocked: Boolean
)