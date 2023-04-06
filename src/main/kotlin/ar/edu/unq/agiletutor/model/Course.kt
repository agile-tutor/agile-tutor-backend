package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "course")

class Course:Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_course")
        var id: Long?= null


        @Column(nullable = false)
        @NotNull( "El nonbre es obligatorio")
        // @Size(min = 1, max = 30, message = "el campo name debe contener un minimo de 3 y un máximo de 30 caracteres")
        var name: String? = null



        // @Column(nullable = false)
        @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
        //@OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
        //@Size(min = 1, max = 1)
        var students: MutableSet<Student> = HashSet()


     @ManyToOne(optional = true)
     var tutor: Tutor? = null



    constructor() : super() {}
        constructor(
            id: Long?,
            name: String?,
            students:MutableSet<Student>,
            tutor: Tutor ?

        ) : super() {
            this.id = id
            this.name = name
            this.students = students
            this.tutor = tutor

        }


    }