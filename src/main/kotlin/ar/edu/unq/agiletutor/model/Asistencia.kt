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
    @Column(name = "id_asistence")
    var id: Int?= null


    @Column(nullable = false)
    @NotNull("el day es obligatorio")
   // @Size(min = 1, max = 1, message = "el campo day debe tener un solo caracter")
    var day: Int?= null


    @Column(nullable = false)
    @NotNull("el check es obligatorio")
   // @Size(min = 1, max = 1, message = "el campo check debe tener un solo caracter")
    var check: String?= null



    constructor() : super() {}
    constructor(
        id: Int?,
        day: Int?,
        check: String
           ) : super() {
        this.id = id
        this.day = day
        this.check = check

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