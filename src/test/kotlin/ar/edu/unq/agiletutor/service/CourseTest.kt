package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.model.*
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CourseTest{

    var tutors = listOf<Tutor>()
    var students = listOf<Student>()

    lateinit var student1: Student
    lateinit var student2: Student
    lateinit var studentData: StudentDTO

    lateinit var course: Course

    lateinit var tutor1: Tutor
    lateinit var tutor2: Tutor
    lateinit var tutorData: TutorRegisterDTO


    @BeforeEach
    fun setUp() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())

        course = Course(0, "c0", mutableSetOf(), tutor1, mutableSetOf(),0.00)
        val dateclasses = mutableSetOf<DateClass>()
        for (i in (1..6)) {
            dateclasses.add(DateClass(0,i, false,course))
        }

        course = Course(0, "c0", mutableSetOf(), tutor1, dateclasses, 0.00)


    }
    /** Mark down a Course from a particular day */
    @Test
    fun Al_consultar_por_un_dia_invalido_Lanza_excepcion() {
        assertThrows<ItemNotFoundException> {  course.markAttendanceAtaDay(0) }

    }

    @Test
    fun Al_consultar_por_un_dia_que_se_paso_asistencia_devuelve_True(){
        val day = 6
        course.markAttendanceAtaDay(day)
        val dateClass = course.dateclasses.toMutableList().get(day.dec())
        Assertions.assertTrue(dateClass.passed)
    }


    @Test
    fun Al_consultar_por_un_dia_que_no_se_paso_asistencia_devuelve_False(){
        val day = 1
        val dayNotPassed = 6
        course.markAttendanceAtaDay(day)
        val dateClass = course.dateclasses.toMutableList().get(dayNotPassed.dec())
        Assertions.assertFalse(dateClass.passed)
    }

    @AfterEach
    fun tearDown() {

    }


}