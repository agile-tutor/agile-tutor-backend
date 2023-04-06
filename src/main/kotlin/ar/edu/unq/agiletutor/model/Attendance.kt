
package ar.edu.unq.agiletutor.model




import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity
class Attendance:Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    var id: Int?= null


    @Column(nullable = false)
    @NotNull("el day es obligatorio")
   // @Size(min = 1, max = 1, message = "el campo day debe tener un solo caracter")
    var day: Int?= null


    @Column(nullable = false)
    @NotNull("el check es obligatorio")
   // @Size(min = 1, max = 1, message = "el campo check debe tener un solo caracter")
    var check: String?=null

    @JoinColumn(nullable = false)
    @ManyToOne(optional = true)
    var alumno: Student? = null




    constructor() : super() {}
    constructor(
        id: Int?,
        day: Int?,
        check: String,
        alumno: Student?
           ) : super() {
        this.id = id
        this.day = day
        this.check = check
        this.alumno = alumno

    }

    fun setday(day: Int){
        this.day = day
    }

    fun getcday():Int? {
        return day
    }


    fun setcheck(check: String){
        this.check = check
    }


    fun getcheck():String? {
        return check
    }



    fun getAsistence():Int{
        if ( getcheck()=="presente" )
        {return 1}
        else { return 0}
    }


}

