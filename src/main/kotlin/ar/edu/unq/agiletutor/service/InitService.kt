package ar.edu.unq.agiletutor.service


import ar.edu.unq.agiletutor.StudentRegisterMapper
import ar.edu.unq.agiletutor.model.Alumno
import jakarta.annotation.PostConstruct
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InitService {

    protected val logger = LogFactory.getLog(javaClass)

    @Value("\${spring.datasource.driverClassName:NONE}")
    private val className: String? = null

    @Autowired
    private val studentService: StudentService? = null

    @Autowired
    private lateinit var senderService: EmailServiceImpl

    @PostConstruct
    fun initialize() {

        if (className == "com.mysql.cj.jdbc.Driver") {
            logger.info("Init Data Using Mysql DB")
            fireInitialData()
        }
    }

    private fun fireInitialData() {

        senderService.sendSimpleMessage("cristian.gonzalez.unq@gmail.com","probando","este es el mensaje")
        if (studentService!!.findAll().isEmpty()) {

            studentService.register(Alumno("Ale", "Fariña", "012", "ale@gmail.com", mutableSetOf(), 0.0, ""))
            studentService.register(Alumno("Cristian", "Gonzalez", "345", "cristian@gmail.com", mutableSetOf(), 0.0, ""))
            studentService.register(Alumno("Ada", "Lovelace", "456", "lovelace@gmail.com", mutableSetOf(), 75.0, ""))
            studentService.register(Alumno("Alan", "Turing", "678", "turing@gmail.com", mutableSetOf(), 70.0, ""))
            studentService.register(Alumno("Donald", "Knuth", "901", "knuth@gmail.com", mutableSetOf(), 76.0, ""))
            studentService.register(Alumno("Dennis", "Ritchie", "234", "ritchie@gmail.com", mutableSetOf(), 15.0, ""))
            studentService.register(Alumno("Richard", "Stallman", "567", "stallman@gmail.com", mutableSetOf(), 8.0, ""))
            studentService.register(Alumno("Bjarne", "Stroustrup", "890", "stroustrup@gmail.com", mutableSetOf(), 100.0, ""))
            studentService.register(Alumno("Tim", "Berners-Lee", "123", "berners-Lee@gmail.com", mutableSetOf(), 95.0, ""))
            studentService.register(Alumno("Alan", "Cooper", "789", "cooper@gmail.com", mutableSetOf(), 25.0, ""))
            studentService.register(Alumno("Linus", "Torvalds", "987", "torvalds@gmail.com", mutableSetOf(), 50.0, ""))

        }
   }
}