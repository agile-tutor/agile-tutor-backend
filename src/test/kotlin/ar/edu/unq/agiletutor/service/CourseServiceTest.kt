package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.model.Attendance
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CourseServiceTest {


    @Autowired
    lateinit var studentService : StudentService

    @Autowired
    lateinit var tutorRepository: TutorRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var studentRepository: StudentRepository


    @Autowired
    private val courseService: CourseService? = null

    @Autowired
    private val tutorService: TutorService? = null


    var tutors = listOf<Tutor>()
    var students = listOf<Student>()

    lateinit var student1: Student
    lateinit var student2: Student
    lateinit var studentData: StudentDTO

    lateinit var course1Saved: Course

    lateinit var tutor1: Tutor
    lateinit var tutor2: Tutor
    lateinit var tutorData: TutorRegisterDTO


    @BeforeEach
    fun setUp() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())
        val tutor2 = Tutor(0, "tutor2", "ape2", "tutor2@gmail.com", "passtut2", mutableSetOf())
        val tutor1saved = tutorService!!.register(tutor1)
        val tutor2saved = tutorService.register(tutor2)

        val course0 = Course(0, "c0", mutableSetOf(), tutor1saved, mutableSetOf())
        val course1 = Course(0, "c1", mutableSetOf(), tutor1saved,mutableSetOf())
        val course2 = Course(0, "c2", mutableSetOf(), tutor1saved,mutableSetOf())
        val course3 = Course(0, "c3", mutableSetOf(), tutor2saved,mutableSetOf())
        val course4 = Course(0, "c4", mutableSetOf(), tutor2saved,mutableSetOf())


        //val courseWithoutStudents = courseService!!.register(course0)
        course1Saved = courseService!!.register(course1)
        val course2Saved = courseService.register(course2)
        val course3Saved = courseService.register(course3)
        val course4Saved = courseService.register(course4)

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

        studentData =
            StudentDTO(
                0,
                "Ale",
                "Trucho",
                "500",
                "mailfalso@gmail.com",

                mutableListOf(),
                0.0,
                "Uma Observación",
                true
            )

        student1 =
            Student(
                0,
                "Ale",
                "Fariña",
                "123",
                "algoritmosale@gmail.com",
                firstattendaces,
                0.0,
                "",
                course1Saved,
                false
            )
        // val studentregistered1 = studentService!!.register(student1)

        student2 = Student(
            0,
            "Cristian",
            "Gonzalez",
            "456",
            "cristian.gonzalez.unq@gmail.com",
            firstattendaces,
            0.0,
            "",
            course1Saved,
            false
        )
        // val studentregistered2 = studentService.register(student2)

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
        //  val studentregistered3 = studentService.register(student3)

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
        //    val studentregistered4  =  studentService.register(student4)

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
        // val studentregistered5  =studentService.register(student5)

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
        //  val studentregistered6  = studentService.register(student6)

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
        //  val studentregistered7  =  studentService.register(student7)

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
        //  val studentregistered8  = studentService.register(student8)

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
        //  val studentregistered9= studentService.register(student9)

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
        // val studentregistered10   =studentService.register(student10)

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
        //  val studentregistered211 =  studentService.register(student11)

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
        //  val studentregistered12  = studentService.register(student12)

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
        //  val studentregistered13 =  studentService.register(student13)

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
        // val studentregistered14  = studentService.register(student14)

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
        //  val studentregistered15  = studentService.register(student15)

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
        //  val studentregistered16  = studentService.register(student16)

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
        // val studentregistered17  =  studentService.register(student17)

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
        //  val studentregistered18 =  studentService.register(student18)


    }

    /** Students from a course */
    @Test
    fun al_consultar_los_estudiantes_de_un_curso_no_existente_Lanza_Excepcion(){
        assertThrows<ItemNotFoundException> {  courseService!!.studentsFromACourse(0)}
    }



    @Test
    fun al_consultar_Los_estudiantes_de_un_curso_vacio_no_devuelve_estudiantes() {
        val students = courseService!!.studentsFromACourse(course1Saved.id!!)
        Assertions.assertTrue(students.isEmpty())
    }


    @Test
    fun al_consultar_Los_estudiantes_de_un_curso_no_vacio_devuelve_sus_estudiantes() {
        val courseId = course1Saved.id!!
        tutorService!!.addAStudentToACourse(student1,course1Saved)
        tutorService!!.addAStudentToACourse(student2,course1Saved,)
        val students = courseService!!.studentsFromACourse(courseId)
        Assertions.assertTrue(students.isNotEmpty())
        Assertions.assertEquals(students.size, 2)
    }

    @AfterEach
    fun tearDown() {
       studentRepository.deleteAll()
       courseRepository.deleteAll()
       tutorRepository.deleteAll()
    }

}