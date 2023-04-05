package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

//import javax.persistence.*
//import kotlin.jvm.Transient

@Entity
@Table(name = "asistencias")
class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attendance")
    var id: Long? = 0

    @Column(nullable = false)
    @NotNull("el day es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo day debe tener un solo caracter")
    var day: Int? = null

    @Column(nullable = false)
    @NotNull("el check es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo check debe tener un solo caracter")
    var attended: Boolean? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id")
    var alumno: Alumno? = null

    constructor() : super() {}
    constructor(
        alumno: Alumno?,
        day: Int?
    ) : super() {
        this.alumno = alumno
        this.day = day
        this.attended = false
    }

    fun setday(day: Int) {
        this.day = day
    }

    fun getday(): Int? {
        return day
    }

    fun setattended(attended: Boolean) {
        this.attended = attended
    }

    fun getattended(): Boolean? {
        return attended
    }
}