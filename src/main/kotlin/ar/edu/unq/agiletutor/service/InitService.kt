package ar.edu.unq.agiletutor.service


import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
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
    private val courseService: CourseService? = null

    @Autowired
    private val tutorService: TutorService? = null

    @PostConstruct
    fun initialize() {

        if (className == "com.mysql.cj.jdbc.Driver") {
            logger.info("Init Data Using Mysql DB")
            if (studentService!!.findAll().isEmpty()) {
                fireInitialData()
            }
        }
    }

    private fun fireInitialData() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())
        val tutor2 = Tutor(0, "tutor2", "ape2", "tutor2@gmail.com", "passtut2", mutableSetOf())
        val tutor1saved = tutorService!!.register(tutor1)
        val tutor2saved = tutorService!!.register(tutor2)

        val course1 = Course(0, "c1", mutableSetOf(), tutor1saved)
        val course2 = Course(0, "c2", mutableSetOf(), tutor1saved)
        val course3 = Course(0, "c3", mutableSetOf(), tutor2saved)
        val course4 = Course(0, "c4", mutableSetOf(), tutor2saved)

        val course1Saved = courseService!!.register(course1)
        val course2Saved = courseService!!.register(course2)
        val course3Saved = courseService!!.register(course3)
        val course4Saved = courseService!!.register(course4)


        val firstattendaces = mutableSetOf<Attendance>()
        for (i in (1..6)) {
            firstattendaces.add(Attendance(i, false))
        }

        val atendances = mutableSetOf<Attendance>()
        atendances.add(Attendance(1, true))
        atendances.add(Attendance(2, true))
        atendances.add(Attendance(3, true))
        atendances.add(Attendance(4, true))
        atendances.add(Attendance(5, true))
        atendances.add(Attendance(6, true))

        val attendancesSecond = atendances.map { AttendanceDTO.desdeModelo(it) }


        val student1 =
            Student(
                0,
                "Ale",
                "Fariña",
                "123",
                "ale@gmail.com",
                firstattendaces,
                0.0,
                "",
                course1Saved,
                false
            )
        val studentregistered1 = studentService!!.register(student1)

        val student2 = Student(
            0,
            "Cristian",
            "Gonzalez",
            "456",
            "cristian@gmail.com",
            firstattendaces,
            0.0,
            "",
            course1Saved,
            false
        )
        val studentregistered2 = studentService!!.register(student2)

        val student3 =
            Student(
                0, "Pedro",
                "Picapiedra",
                "456",
                "pica@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student3)

        val student4 =
            Student(
                0,
                "Pablo",
                "Marmol",
                "456",
                "marmol@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student4)

        val student5 =
            Student(
                0, "Super",
                "Sonico",
                "456",
                "super@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student5)

        val student6 =
            Student(
                0,
                "Jane",
                "Jetson",
                "456",
                "jane@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student6)

        val student7 =
            Student(
                0,
                "Alu3",
                "Marmol",
                "456",
                "alu3@gmail.com",
                firstattendaces,
                0.0,
                "",
                course4Saved,
                false
            )
        studentService!!.register(student7)


        val student8 =
            Student(
                0,
                "Alu4",
                "Marmol",
                "456",
                "alu4@gmail.com",
                firstattendaces,
                0.0,
                "",
                course4Saved,
                false
            )
        studentService!!.register(student8)

        val student9 =
            Student(
                0, "Pebbles",
                "Picapiedra",
                "456",
                "pebbles@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student9)

        val student10 =
            Student(
                0,
                "Bam-Bam",
                "Marmol",
                "456",
                "bambam@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student10)

        val student11 =
            Student(
                0, "Vilma",
                "Picapiedra",
                "456",
                "vilma@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student11)

        val student12 =
            Student(
                0,
                "Betty",
                "Marmol",
                "456",
                "betty@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student12)

        val student13 =
            Student(
                0, "Mr",
                "Slate",
                "456",
                "slate@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student13)

        val student14 =
            Student(
                0,
                "Creepella",
                "Gruesome",
                "456",
                "creepella@gmail.com",
                firstattendaces,
                0.0,
                "",
                course2Saved,
                false
            )
        studentService!!.register(student14)

        val student15 =
            Student(
                0, "Elroy",
                "Jetson",
                "456",
                "elroy@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student15)

        val student16 =
            Student(
                0,
                "Lucero",
                "Sonico",
                "456",
                "lucero@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student16)

        val student17 =
            Student(
                0, "Senor",
                "Espacial",
                "456",
                "espacial@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student17)

        val student18 =
            Student(
                0,
                "Mrs",
                "Spacely",
                "456",
                "spacely@gmail.com",
                firstattendaces,
                0.0,
                "",
                course3Saved,
                false
            )
        studentService!!.register(student18)

        studentService.updateattendances(studentregistered1.id!!, attendancesSecond)

    }
}