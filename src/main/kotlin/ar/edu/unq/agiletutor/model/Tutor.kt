package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "tutor")

class Tutor : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutor")
    var id: Int? = null

    @Column(nullable = false)
    @NotNull("El nonbre es obligatorio")
    var name: String? = null

    @Column(nullable = false)
    @NotNull("el apellido es obligatorio")
    var surname: String? = null


    @Column(nullable = false, unique = true)
    @NotNull("El mail es obligatorio")
    var email: String? = null

    @Column
    @NotNull("El password es obligatorio")
    var password: String? = ""

    // @Column(nullable = false)
    @OneToMany(mappedBy = "tutor", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var courses: MutableSet<Course> = HashSet()

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var notifyer: Notifyer? = null

    constructor() : super() {}
    constructor(
            id: Int?,
            name: String?,
            surname: String?,
            email: String?,
            password: String?,
            courses: MutableSet<Course>,

            ) : super() {
        this.id = id
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.courses = courses
    }
}