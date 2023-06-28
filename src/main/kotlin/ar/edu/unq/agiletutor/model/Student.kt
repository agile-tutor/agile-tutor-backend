package ar.edu.unq.agiletutor.model

import ar.edu.unq.agiletutor.service.AttendanceDTO
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "student")
class Student : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    var id: Long? = null

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

    //@Column(nullable = false)
    @OneToMany(/*mappedBy = "student",*/ cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    //@JoinColumn(nullable = false)
    var attendances: MutableSet<Attendance> = HashSet()

    @Column(nullable = false)
    var attendancepercentage: Double = 0.0

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
        attendancepercentage: Double,
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
        this.attendancepercentage = attendancepercentage
        this.observations = observations
        this.course = course
        this.blocked = blocked
    }

    fun attended(): List<Attendance> {
        return attendances.filter { it.attended }
    }

    fun attendedDay(day: Int): Boolean {
        return attendances.any { it.day == day && it.attended }
    }

    fun absent(): List<Attendance> {
        return attendances.filter { !(it.attended) }
    }

    fun cantidadDePresentes(): Int {
        return attended().size
    }

    fun cantidadDeAusentes(): Int {
        return absent().size
    }
/*
    fun calcularPorcentajeDeAsistencias(): Double {
        attendancepercentage = (cantidadDePresentes() * (100 / 6)).toDouble()
        return attendancepercentage
    }
  */

    fun updateAttendanceAtADay (attendance: Attendance) {
        println("updateatendance"+attendance.toString())
        attendances.toMutableList().set(attendance.day!!, attendance)
        updateAttendancePercentage()
    }



    fun updateAttendancePercentage(){
        attendancepercentage = (cantidadDePresentes() * (100 / 6)).toDouble()
    }

    fun attendancePercentage(): Double{
        return attendancepercentage
    }

    fun sinFaltas(): Boolean {
        // return ( attendances.size == cantidadDePresentes() )
        return attendances.all { it.attended }
    }
}