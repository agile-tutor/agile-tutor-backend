package ar.edu.unq.agiletutor.model

import ar.edu.unq.agiletutor.ItemNotFoundException
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "course")

class Course : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    var id: Int? = null

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

    @OneToMany( cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var dateclasses: MutableSet<DateClass> = HashSet()

    constructor() : super() {}
    constructor(
        id: Int?,
        name: String?,
        students: MutableSet<Student>,
        tutor: Tutor?,
        dateclasses: MutableSet<DateClass>

    ) : super() {
        this.id = id
        this.name = name
        this.students = students
        this.tutor = tutor
        this.dateclasses = dateclasses    }

    fun markAttendanceAtaDay(day :Int):Course {
        val rangedays = (1..6)
        if (! rangedays.contains(day)) {
            throw ItemNotFoundException(" Day:  $day invalid")
        }
        val dateclass = dateclasses.toMutableList().get(day.dec())
        dateclass.passed = true
        dateclasses.toMutableList().set(day.dec(), dateclass)
        return this
    }

}
