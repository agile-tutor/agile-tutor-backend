package ar.edu.unq.agiletutor.model

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.service.DayBooleanDTO
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "course")

class Course : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_course")
    var id: Int = 0

    @Column(nullable = false)
    @NotNull("El nonbre es obligatorio")
    // @Size(min = 1, max = 30, message = "el campo name debe contener un minimo de 3 y un máximo de 30 caracteres")
    var name: String? = null

    // @Column(nullable = false)
    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    //@OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
    //@Size(min = 1, max = 1)
    var students: MutableSet<Student> = HashSet()

    @ManyToOne(optional = true)
    var tutor: Tutor? = null

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var meetings: MutableSet<Meeting> = HashSet()


    constructor() : super() {}
    constructor(
            id: Int,
            name: String?,
            students: MutableSet<Student>,
            tutor: Tutor?,
            meetings: MutableSet<Meeting>


    ) : super() {
        this.id = id
        this.name = name
        this.students = students
        this.tutor = tutor
        this.meetings = meetings

    }

    fun markAttendanceAtaDay(day: Int): Course {
        val rangedays = (1..6)
        if (!rangedays.contains(day)) {
            throw ItemNotFoundException(" Day:  $day invalid")
        }
        println("aca llegue")
        val meeting = meetings.toMutableList()[day.dec()]
        meeting.passed = true
        println("a ver" + meeting.day.toString() + meeting.passed.toString())
        meetings.toMutableList()[day.dec()] = meeting
        meetings.forEach { println(it.day.toString() + it.passed.toString()) }
        return this
    }

    fun markedDowndDay(day: Int): Boolean {
        return meetings.any { it.day == day && it.passed }
    }

    fun attendedAtDays(): MutableSet<DayBooleanDTO> {
        val daysAttended: MutableSet<DayBooleanDTO> = HashSet()
        val day1 = students.filter { it.attendedDay(1) }.size
        val day2 = students.filter { it.attendedDay(2) }.size
        val day3 = students.filter { it.attendedDay(3) }.size
        val day4 = students.filter { it.attendedDay(4) }.size
        val day5 = students.filter { it.attendedDay(5) }.size
        val day6 = students.filter { it.attendedDay(6) }.size

        daysAttended.add(DayBooleanDTO(1, day1 > 0))
        daysAttended.add(DayBooleanDTO(2, day2 > 0))
        daysAttended.add(DayBooleanDTO(3, day3 > 0))
        daysAttended.add(DayBooleanDTO(4, day4 > 0))
        daysAttended.add(DayBooleanDTO(5, day5 > 0))
        daysAttended.add(DayBooleanDTO(6, day6 > 0))

        return daysAttended
    }
}

