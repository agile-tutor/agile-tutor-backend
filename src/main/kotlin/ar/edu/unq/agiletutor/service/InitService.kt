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
    @PostConstruct
    fun initialize() {

        if (className == "com.mysql.cj.jdbc.Driver") {
            logger.info("Init Data Using Mysql DB")
            fireInitialData()
        }
    }

    private fun fireInitialData() {
        val student1 = Alumno(  0,"Ale","Fariña", "123","ale@gmail.com", mutableSetOf() ,0.0,""  )
       studentService!!.register(student1)

        val student2= Alumno( 0,"Cristian", "Gonzalez","456","cristian@gmail.com",mutableSetOf() ,0.0,"")
        studentService!!.register(student2)
    }
}