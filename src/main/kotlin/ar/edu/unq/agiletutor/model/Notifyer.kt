package ar.edu.unq.agiletutor.model

import jakarta.persistence.*

@Entity
@Table(name = "notifyer")
class Notifyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "id")
    var absent: MutableSet<Alumno> = HashSet()

    var textemail: String = "probando-cambiar"

    var subjectmail: String = ""

    fun getabsent(): MutableSet<Alumno>? {
        return absent
    }

    fun getTextEmail(): String {
        return this.textemail
    }

    fun getSubjectEmail(): String {
        return this.subjectmail
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
        this.absent = HashSet()
    }
}