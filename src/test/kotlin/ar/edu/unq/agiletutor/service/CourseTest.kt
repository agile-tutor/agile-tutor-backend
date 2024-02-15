package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.model.*
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CourseTest{

    lateinit var course: Course

    @BeforeEach
    fun setUp() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())

        course = Course(0, "c0", mutableSetOf(), tutor1, mutableSetOf())
        val meetings = mutableSetOf<Meeting>()
        for (i in (1..6)) {
            meetings.add(Meeting(0,i, "2024-02-1$i", "a definir",false,course))
        }

        course = Course(0, "c0", mutableSetOf(), tutor1, meetings)


    }
    /*
    /** Mark down a Course from a particular day */
    @Test
    fun al_consultar_por_un_dia_invalido_Lanza_excepcion() {
        assertThrows<ItemNotFoundException> {  course.markAttendanceAtaDay(0) }
    }

    @Test
    fun al_consultar_por_un_dia_que_se_paso_asistencia_devuelve_True(){
        val day = 6
        course.markAttendanceAtaDay(day)
        val meeting = course.meetings.toMutableList()[day.dec()]
        Assertions.assertTrue(meeting.passed)
    }

    @Test
    fun al_consultar_por_un_dia_que_no_se_paso_asistencia_devuelve_False(){
        val day = 1
        val dayNotPassed = 6
        course.markAttendanceAtaDay(day)
        val meeting = course.meetings.toMutableList()[dayNotPassed.dec()]
        Assertions.assertFalse(meeting.passed)
    }
*/
    @AfterEach
    fun tearDown() {
    }
}