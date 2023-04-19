package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable
//import javax.persistence.*

@Entity
@Table(name = "student")
class Student: Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    var id: Long?= null


    @Column(nullable = false)
    @NotNull( "El nonbre es obligatorio")
    var name: String? = null

    @Column(nullable = false)
    @NotNull( "el apellido es obligatorio")
    var surname: String? = null



    @Column(nullable = false)
    @NotNull("El número identificador es obligatorio")
    var identifier: String? = null

    @Column(nullable = false, unique = true)
    @NotNull("El mail es obligatorio")
    var email: String? = null


  //@Column(nullable = false)
   @OneToMany(/*mappedBy = "student",*/ cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
   //@JoinColumn(nullable = false)
   var attendances: MutableSet<Attendance> = HashSet()


    @Column(nullable = false)
    var attendancepercentage: Double = 0.0

    @Column
    var observations: String = ""

    @ManyToOne(optional = true)
    var course: Course? = null


    constructor() : super() {}
    constructor(
        id: Long?,
        name: String?,
        surname: String?,
        identifier: String?,
        email: String?,
        attendances:MutableSet<Attendance>,
        attendancepercentage: Double,
        observations: String,
        course:Course?
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

    }

     fun  presentes(): List<Attendance> {
       return  attendances.filter { it.attended }
    }

     fun  ausentes(): List<Attendance> {
        return  attendances.filter { ! (it.attended) }
    }

     fun cantidadDePresentes(): Int {
       return  presentes().size
    }

     fun cantidadDeAusentes(): Int {
        return  ausentes().size
    }

     fun calcularPorcentajeDeAsistencias():Double {
        attendancepercentage = ( cantidadDePresentes() * (100/6)).toDouble()
         return attendancepercentage
    }

    fun sinFaltas():Boolean{
       return ( attendances.size == cantidadDePresentes() )
    }


}