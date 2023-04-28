package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "notifyer")
class Notifyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var absent: MutableSet<Alumno> = HashSet()

    var textemail: String =
        "¿Como estás? Observo que te ausentaste al encuentro del \"taller de vida universitaria\", y ¡quería saber si te pasó algo!\n" +
                "\n" +
                "Saludos\n" +
                "Cristian"

    var subjectmail: String = "ASISTENCIAS - TVU"

    fun getabsent(): MutableSet<Alumno>? {
        println("getabsent"+this.absent)
        return this.absent
    }

    fun getTextEmail(nombre: String): String {
        return "Buenas noches "+nombre+",\n" +
                "\n" +
                this.textemail
    }

    fun getSubjectEmail(): String {
        return this.subjectmail + " " + LocalDate.now().year
    }

    fun setTextEmail(text: String) {
        this.textemail = text
    }

    fun setSubjectEmail(text: String) {
        this.subjectmail = text
    }

    fun addabsent(alumno: Alumno) {
        this.absent.add(alumno)
    }

    fun delabsent(alumno: Alumno) {
        this.absent.remove(alumno)
    }

    fun removeall() {
        val studentsToRemove: MutableSet<Alumno> = this.absent
        this.absent.removeAll(studentsToRemove)
        println("aquipaso"+this.absent+"aquipase")
    }
}