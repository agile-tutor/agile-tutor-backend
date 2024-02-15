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
    lateinit var tutorRepository: TutorRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Autowired
    private val courseService: CourseService? = null

    @Autowired
    private val tutorService: TutorService? = null

    @Autowired
    private val studentService: StudentService? = null

    lateinit var student1: Student
    lateinit var student2: Student
    lateinit var studentData: StudentRegisterDTO
    lateinit var course1Saved: Course

    @BeforeEach
    fun setUp() {
        tutorRepository.deleteAll()
        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())
        val tutor1saved = tutorService!!.register(tutor1)

        val course1 = Course(0, "c1", mutableSetOf(), tutor1saved, mutableSetOf())

        course1Saved = courseService!!.register(course1)

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

        studentData =
                StudentRegisterDTO(
                        0,
                        "Ale",
                        "Trucho",
                        "500",
                        "mailfalso@gmail.com",
                        "Uma Observación",
                        999
                )

        student1 =
                Student(
                        0,
                        "Ale",
                        "Fariña",
                        "123",
                        "algoritmosale@gmail.com",
                        firstattendaces,
                        "",
                        course1Saved,
                        false
                )

        student2 = Student(
                0,
                "Cristian",
                "Gonzalez",
                "456",
                "cristian.gonzalez.unq@gmail.com",
                firstattendaces,
                "",
                course1Saved,
                false
        )
    }

    /** Students from a course */
    @Test
    fun al_consultar_los_estudiantes_de_un_curso_no_existente_Lanza_Excepcion() {
        assertThrows<ItemNotFoundException> { courseService!!.studentsFromACourse(0) }
    }

    @Test
    fun al_consultar_Los_estudiantes_de_un_curso_vacio_no_devuelve_estudiantes() {
        val students = courseService!!.studentsFromACourse(course1Saved.id!!)
        Assertions.assertTrue(students.isEmpty())
    }

    @Test
    fun al_consultar_Los_estudiantes_de_un_curso_no_vacio_devuelve_sus_estudiantes() {
        val courseId = course1Saved.id!!
        studentService!!.register(student1)
        studentService.register(student2)
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