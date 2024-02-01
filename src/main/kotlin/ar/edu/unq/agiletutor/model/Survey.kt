package ar.edu.unq.agiletutor.model

import jakarta.persistence.*

@Entity
@Table(name = "survey")
class Survey : BaseEntity {

    var studentId: Long = 0

    var city: String = ""

    var haveWiFi: Boolean = false

    var haveDatos: Boolean = false

    var havePc: Boolean = false

    var haveNotebook: Boolean = false

    var haveCellphone: Boolean = false

    var haveTablet: Boolean = false

    var environment: String = ""

    var exclusive: String = ""

    var hardwareState: String = ""

    constructor() : super() {}
    constructor(studentId: Long, city: String, haveWiFi: Boolean, haveDatos: Boolean, havePc: Boolean, haveNotebook: Boolean,
                haveCellphone: Boolean, haveTablet: Boolean, environment: String, exclusive: String, hardwareState: String)
            : super() {
        this.studentId = studentId
        this.city = city
        this.haveWiFi = haveWiFi
        this.haveDatos = haveDatos
        this.havePc = havePc
        this.haveNotebook = haveNotebook
        this.haveCellphone = haveCellphone
        this.haveTablet = haveTablet
        this.environment = environment
        this.exclusive = exclusive
        this.hardwareState = hardwareState
    }
}