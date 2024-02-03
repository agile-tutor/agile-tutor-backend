package ar.edu.unq.agiletutor.service

import ar.edu.unq.agilemeeting.service.MeetingRegisterDTO
import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.model.*
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.MeetingRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class StudentServiceTest {

    @Autowired
    lateinit var studentService: StudentService

    @Autowired
    lateinit var tutorRepository: TutorRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Autowired
    lateinit var meetingRepository: MeetingRepository

    @Autowired
    private val courseService: CourseService? = null

    @Autowired
    private val tutorService: TutorService? = null

    @Autowired
    private val meetingService: MeetingService? = null

    var students = listOf<Student>()

    lateinit var student1: Student
    lateinit var student2: Student
    lateinit var student5: Student
    lateinit var student6: Student
    lateinit var student15: Student
    lateinit var student16: Student
    lateinit var student17: Student
    lateinit var studentData: StudentRegisterDTO
    lateinit var course1Saved : Course

    @BeforeEach
    fun setUp() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())
        val tutor2 = Tutor(0, "tutor2", "ape2", "tutor2@gmail.com", "passtut2", mutableSetOf())
        val tutor1saved = tutorService!!.register(tutor1)
        val tutor2saved = tutorService.register(tutor2)

        val course1 = Course(0, "c1", mutableSetOf(), tutor1saved, mutableSetOf())
        val course3 = Course(0, "c3", mutableSetOf(), tutor2saved, mutableSetOf())

        course1Saved = courseService!!.register(course1)
        val course3Saved = courseService.register(course3)

        val meeting1 = MeetingRegisterDTO(0, "1", "", 1)
        val meeting2 = MeetingRegisterDTO(0, "2", "", 2)
        val meeting3 = MeetingRegisterDTO(0, "3", "", 3)
        val meeting4 = MeetingRegisterDTO(0, "4", "", 4)
        val meeting5 = MeetingRegisterDTO(0, "5", "", 5)
        val meeting6 = MeetingRegisterDTO(0, "6", "", 6)

        meetingService!!.register(meeting1)
        meetingService.register(meeting2)
        meetingService.register(meeting3)
        meetingService.register(meeting4)
        meetingService.register(meeting5)
        meetingService.register(meeting6)

        val firstattendaces = mutableSetOf<Attendance>()
        for (i in (1..6)) {
            firstattendaces.add(Attendance(i, true))
        }

        val atendances = mutableSetOf<Attendance>()
        atendances.add(Attendance(1, true))
        atendances.add(Attendance(2, true))
        atendances.add(Attendance(3, true))
        atendances.add(Attendance(4, true))
        atendances.add(Attendance(5, false))
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

        student5 =
                Student(
                        0, "Super",
                        "Sonico",
                        "456",
                        "super@gmail.com",
                        atendances,
                        "",
                        course3Saved,
                        false
                )

        student6 =
                Student(
                        0,
                        "Jane",
                        "Jetson",
                        "456",
                        "jane@gmail.com",
                        atendances,
                        "",
                        course3Saved,
                        false
                )

        student15 =
                Student(
                        0, "Elroy",
                        "Jetson",
                        "456",
                        "elroy@gmail.com",
                        atendances,
                        "",
                        course3Saved,
                        false
                )

        student16 =
                Student(
                        0,
                        "Lucero",
                        "Sonico",
                        "456",
                        "lucero@gmail.com",
                        firstattendaces,
                        "",
                        course3Saved,
                        false
                )

        student17 =
                Student(
                        0, "Senor",
                        "Espacial",
                        "456",
                        "espacial@gmail.com",
                        firstattendaces,
                        "",
                        course3Saved,
                        false
                )
    }

    /**get  Students */
    @Test
    fun al_solicitar_a_una_DB_sin_estudiantes_no_devuelve_ninguno() {
        students = studentService.findAll()
        Assertions.assertTrue(students.isEmpty())
    }

    @Test
    fun al_solicitar_a_La_DB_todos_los_estudiantes_Devuelve_la_cantidad_de_estudiantes_registrados() {
        studentService.register(student1)
        studentService.register(student2)
        students = studentService.findAll()
        Assertions.assertTrue(students.isNotEmpty())
        Assertions.assertEquals(2, students.size)
    }


    /** find a Student By Id */
    @Test
    fun al_intentar_buscar_un_estudiante_con_id_no_existente_Lanza_excepcion() {
        studentService.register(student1)
        assertThrows<ItemNotFoundException> { studentService.findByID(0) }
    }

    @Test
    fun si_el_id_es_existente_Retorna_al_estudiante_asociado_con_ese_id_() {
        val studentRegistered = studentService.register(student1)
        val studentFound = studentService.findByID(studentRegistered.id!!)
        Assertions.assertEquals(studentRegistered.id, studentFound.id)
        Assertions.assertEquals(studentRegistered.name, studentFound.name)
        Assertions.assertEquals(studentRegistered.surname, studentFound.surname)
        Assertions.assertEquals(studentRegistered.email, studentFound.email)
    }

    /** update a student* */
    @Test
    fun al_intentar_actualizar_un_estudiante_con_id_no_existente_Lanza_excepcion() {
        studentService.register(student1)
        assertThrows<ItemNotFoundException> { studentService.update(0, studentData) }
    }

    @Test
    fun si_el_id_es_existente_Actualiza_al_estudiante_asociado_con_ese_id_() {
        val studentRegistered = studentService.register(student1)
        val updated = studentService.update(studentRegistered.id!!, studentData)
        val restored = studentService.findByID(studentRegistered.id!!)
        Assertions.assertEquals(updated.id, restored.id)
        Assertions.assertEquals(updated.name, restored.name)
        Assertions.assertEquals(updated.surname, restored.surname)
        Assertions.assertEquals(updated.email, restored.email)
        Assertions.assertEquals(updated.attendancepercentage, restored.attendancePercentage())
        Assertions.assertEquals(updated.blocked, restored.blocked)
    }

    /** Delete a student By Id */
    @Test
    fun al_intentar_borrar_un_estudsiante_con_id_no_existente_lanza_excepcionm_y_La_DB_se_mantiene_sin_alterar() {
        studentService.register(student1)
        val students = studentService.findAll()

        assertThrows<ItemNotFoundException> { studentService.deleteById(0) }

        Assertions.assertTrue(students.isNotEmpty())
    }
    /*
        @Test
        fun Si_el_id_es_existente_el_estudiante_asociado_con_ese_id_es_eliminado_() {
            val studentRegistered = studentService.register(student1)
            studentService.deleteById(studentRegistered .id!!)

            val students = studentService.findAll()

            Assertions.assertTrue(students.isEmpty())
        }
    */
    /** Students absent at a particular day */


    @Test
    fun me_devuelve_todos_estudiantes_que_faltaron_en_ese_dia_en_particular() {
        val day = 5
        studentService.register(student5)
        studentService.register(student6)
        studentService.register(student15)
        studentService.register(student16)
        studentService.register(student17)
        val absents = studentService.studentsAbsentAtAParticularDay(day)
        Assertions.assertTrue(absents.isNotEmpty())
        Assertions.assertEquals(absents.size, 3)
    }

    @Test
    fun si_ninguno_falto_ese_dia_en_particular_no_devuelve_estudiantes() {
        val day = 1
        studentService.register(student5)
        studentService.register(student6)
        studentService.register(student15)
        studentService.register(student16)
        studentService.register(student17)
        val absents = studentService.studentsAbsentAtAParticularDay(day)
        Assertions.assertTrue(absents.isEmpty())
    }

    /** Students absent at a particular day */
    @Test
    fun me_devuelve_todos_estudiantes_que_asistieron_un_dia_en_particular() {
        val day = 5
        studentService.register(student5)
        studentService.register(student6)
        studentService.register(student15)
        studentService.register(student16)
        studentService.register(student17)
        val attended = studentService.studentsAttendedAtAParticularDay(day)
        Assertions.assertTrue(attended.isNotEmpty())
        Assertions.assertEquals(attended.size, 2)
    }

    /** block or unblock a students */
    @Test
    fun al_intentar_bloquear_O_daesbloquear_un_estudiante_con_id_no_existente_Lanza_excepcion() {
        studentService.register(student1)
        assertThrows<ItemNotFoundException> { studentService.blockOrUnblockAStudent(0, true) }
    }

    @Test
    fun si_desbloqueo_todos_los_estudiantes_al_consultarlos_apareceran_desbloqueados() {
        val student1 = studentService.register(student1)
        val student2 = studentService.register(student2)
        studentService.blockOrUnblockAStudent(student1.id!!, false)
        studentService.blockOrUnblockAStudent(student2.id!!, false)
        Assertions.assertFalse(student1.blocked)
        Assertions.assertFalse(student2.blocked)
    }

    @Test
    fun si_bloqueo_todos_los_estudiantes_al_consultarlos_apareceran_bloqueados() {
        val student1 = studentService.register(student1)
        val student2 = studentService.register(student2)
        val blockedOrUnblocked1 = studentService.blockOrUnblockAStudent(student1.id!!, true)
        val blockedOrUnblocked2 = studentService.blockOrUnblockAStudent(student2.id!!, true)
        Assertions.assertTrue(blockedOrUnblocked1.blocked)
        Assertions.assertTrue(blockedOrUnblocked2.blocked)
    }

    @Test
    fun si_por_defecto_estan_todos_desbloqueados_y_bloqueo_a_uno_Solo_ese_aparecera_bloqueado() {
        val student1 = studentService.register(student1)
        val student2 = studentService.register(student2)
        val student5 = studentService.register(student5)
        val blockedOrUnblocked1 = studentService.blockOrUnblockAStudent(student1.id!!, true)
        Assertions.assertTrue(blockedOrUnblocked1.blocked)
        Assertions.assertFalse(student2.blocked)
        Assertions.assertFalse(student5.blocked)
    }

    /** check mail */
    @Test
    fun al_consultar_por_un_mail_existente_devuelve_True() {
        val email = "algoritmosale@gmail.com"
        studentService.register(student1)
        studentService.register(student2)
        val checked = studentService.checkMail(email)
        Assertions.assertTrue(checked)
    }

    @Test
    fun al_consultar_por_un_mail_inexistente_devuelve_False() {
        val email = "noexiste@gmail.com"
        studentService.register(student1)
        studentService.register(student2)
        val checked = studentService.checkMail(email)
        Assertions.assertFalse(checked)
    }

    /** Alumno por default no esta bloqueado **/
    @Test
    fun al_consultar_si_esta_bloqueado_un_alumno_nuevo_devuelve_False() {
        val newStudent = studentService.register(studentData.aModelo(course1Saved, meetingService!!.findAll()))
        val checked = studentService.findByID(newStudent.id!!).blocked
        Assertions.assertFalse(checked)
    }

    /** Alumno por default no tiene presentes **/
    @Test
    fun al_consultar_si_tiene_algun_presente_un_alumno_nuevo_devuelve_False() {
        val newStudent = studentService.register(studentData.aModelo(course1Saved, meetingService!!.findAll()))
        val checked = studentService.attendedDays(newStudent.id!!).any { it.attended }
        Assertions.assertFalse(checked)
    }

    /** Alumno por default esta asociado a la cantidad de encuentros creados **/
    @Test
    fun al_consultar_la_correspondencia_entre_meetings_asociados_a_un_alumno_nuevo_y_meetings_disponibles_devuelve_True() {
        val newStudent = studentService.register(studentData.aModelo(course1Saved, meetingService!!.findAll()))
        val meetingsSetted = studentService.attendancesFromAStudent(newStudent.id!!).size
        val meetingsAvailable = meetingService.findAll().size
        Assertions.assertEquals(meetingsSetted, meetingsAvailable)
    }

    @AfterEach
    fun tearDown() {
        meetingRepository.deleteAll()
        studentRepository.deleteAll()
        courseRepository.deleteAll()
        tutorRepository.deleteAll()
    }
}