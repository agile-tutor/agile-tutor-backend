package ar.edu.unq.agiletutor.model


import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
//        @JsonIgnore
        get
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    // @Column(nullable = false)
    @OneToMany(mappedBy = "tutor", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var courses: MutableSet<Course> = HashSet()

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

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