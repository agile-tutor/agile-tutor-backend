package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable


@Entity
@Table(name = "tutor")

class Tutor: Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutor")
    var id: Long?= null


    @Column(nullable = false)
    @NotNull( "El nonbre es obligatorio")
    var name: String? = null

    @Column(nullable = false)
    @NotNull( "el apellido es obligatorio")
    var surname: String? = null



    @Column(nullable = false, unique = true)
    @NotNull("El mail es obligatorio")
    var email: String? = null


    // @Column(nullable = false)
    @OneToMany(mappedBy = "tutor", cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
    var courses: MutableSet<Course> = HashSet()




    constructor() : super() {}
    constructor(
        id: Long?,
        name: String?,
        surname: String?,
        email: String?,
        courses:MutableSet<Course>,

        ) : super() {
        this.id = id
        this.name = name
        this.surname = surname
        this.email = email
        this.courses = courses

    }




}