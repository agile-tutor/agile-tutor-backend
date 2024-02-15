package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "student")
class Student : BaseEntity {

    @Column(nullable = false)
    @NotNull("El nonbre es obligatorio")
    var name: String? = null

    @Column(nullable = false)
    @NotNull("el apellido es obligatorio")
    var surname: String? = null

    @Column(nullable = false)
    @NotNull("El número identificador es obligatorio")
    var identifier: String? = null

    @Column(nullable = false, unique = true)
    @NotNull("El mail es obligatorio")
    var email: String? = null

    @OneToMany(/*mappedBy = "student",*/ cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var attendances: MutableSet<Attendance> = HashSet()

    @Column
    var observations: String = ""

    @ManyToOne(optional = true)
    var course: Course? = null

    @Column
    var blocked: Boolean = false

    constructor() : super() {}
    constructor(
            id: Long?,
            name: String?,
            surname: String?,
            identifier: String?,
            email: String?,
            attendances: MutableSet<Attendance>,
            observations: String,
            course: Course,
            blocked: Boolean
    ) : super() {
        this.id = id
        this.name = name
        this.surname = surname
        this.identifier = identifier
        this.email = email
        this.attendances = attendances
        this.observations = observations
        this.course = course
        this.blocked = blocked
    }

    fun attended(): List<Attendance> {
        return attendances.filter { it.attended }
    }

    fun attendedDay(day: Int): Boolean {
        return attendances.find { it.day == day }!!.attended
    }

    fun meetingDayAttendance(day: Int): Attendance {
        return attendances.find { it.day == day }!!
    }

    fun absent(): List<Attendance> {
        return attendances.filter { !(it.attended) }
    }

    fun cantidadDePresentes(): Int {
        return attended().size
    }

    fun updateAttendanceAtADay(attendance: Attendance) {
        println("updateatendance" + attendance.toString() + attendance.id + attendance.attended)
        //attendances.toMutableList().set(attendance.day!!, attendance)
        attendances.find { it.day == attendance.day }?.attended = attendance.attended
    }

    fun attendancePercentage(): Double {
        return kotlin.math.round(cantidadDePresentes() * (100.00 / 6))
    }

    fun sinFaltas(): Boolean {
        // return ( attendances.size == cantidadDePresentes() )
        return attendances.all { it.attended }
    }

    fun approvedAccordingPercentageDefault(percentage: Double): Boolean {
        return (attendancePercentage() >= kotlin.math.round(percentage))
    }

    fun fillSurvey(studentsIds: List<Long>): Boolean {
        return (studentsIds.contains(id!!))
    }
}