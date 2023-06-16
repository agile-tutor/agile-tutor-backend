package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
@Table(name = "dateclass")
class DateClass: Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dateclass")
    var id: Int? = null

    @Column(nullable = false)
    @NotNull("el day es obligatorio")
    var day: Int? = null

    @Column(nullable = false)
    @NotNull("el passed es obligatorio")
    var passed: Boolean = false

    /*
    @ManyToOne(optional = true)
    var course: Course? = null
*/


    constructor() : super() {}
    constructor(
       //  id: Int?,
        day: Int?,
        passed: Boolean,
      //  course: Course?
        ) : super() {
         //this.id = id
        this.day = day
        this.passed = passed
  //      this.course = course

    }

}