package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
class Attendance : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attendance")
    var id: Int? = null

    @Column(nullable = false)
    @NotNull("el day es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo day debe tener un solo caracter")
    var day: Int? = null

    @Column(nullable = false)
    @NotNull("el attended es obligatorio")
    // @Size(min = 1, max = 1, message = "el campo check debe tener un solo caracter")
    var attended: Boolean = false

    /*
    @JoinColumn(nullable = false)
    @ManyToOne(optional = true)
    var student: Student? = null
*/

    constructor() : super() {}
    constructor(
        // id: Int?,
        day: Int?,
        attended: Boolean,
        //student: Student?
    ) : super() {
        // this.id = id
        this.day = day
        this.attended = attended
    }

    fun setday(day: Int) {
        this.day = day
    }

    fun getcday(): Int? {
        return day
    }
/*
    fun setattended(attended: String) {
        this.attended = attended.toBoolean()
    }*/
/*
    fun getattended(): Boolean? {
        return attended
    }*/
}