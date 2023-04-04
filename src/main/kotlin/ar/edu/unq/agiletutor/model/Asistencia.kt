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
    var id: Long? = null

    @Column(nullable = false)
    @NotNull("el day es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo day debe tener un solo caracter")
    var day: Int? = null

    @Column(nullable = false)
    @NotNull("el check es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo check debe tener un solo caracter")
    var attended: String? = null

    constructor() : super() {}
    constructor(
        id: Long?,
        day: Int?,
        attended: String
    ) : super() {
        this.id = id
        this.day = day
        this.attended = attended

    }

    fun setday(day: Int) {
        this.day = day
    }

    fun getday(): Int? {
        return day
    }

    fun setattended(attended: String) {
        this.attended = attended
    }

    fun getattended(): String? {
        return attended
    }

    fun getasistence(): Int {
        if (getattended() == "presente") {
            return 1
        } else {
            return 0
        }
    }
}