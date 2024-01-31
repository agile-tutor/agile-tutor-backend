package ar.edu.unq.agiletutor.service

import ar.edu.unq.agilemeeting.service.TutorRegisterDTO
import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
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
    private val courseService: CourseService? = null

    @Autowired
    private val tutorService: TutorService? = null


    var tutors = listOf<Tutor>()
    var students = listOf<Student>()

    lateinit var student1: Student
    lateinit var student2: Student
    lateinit var student5: Student
    lateinit var student6: Student
    lateinit var student15: Student
    lateinit var student16: Student
    lateinit var student17: Student
    lateinit var studentData: StudentDTO

    lateinit var tutor1: Tutor
    lateinit var tutor2: Tutor
    lateinit var tutorData: TutorRegisterDTO


    @BeforeEach
    fun setUp() {

        val tutor1 = Tutor(0, "tutor1", "ape1", "tutor1@gmail.com", "passtut1", mutableSetOf())
        val tutor2 = Tutor(0, "tutor2", "ape2", "tutor2@gmail.com", "passtut2", mutableSetOf())
        val tutor1saved = tutorService!!.register(tutor1)
        val tutor2saved = tutorService.register(tutor2)

        val course1 = Course(0, "c1", mutableSetOf(), tutor1saved, mutableSetOf())
        val course2 = Course(0, "c2", mutableSetOf(), tutor1saved, mutableSetOf())
        val course3 = Course(0, "c3", mutableSetOf(), tutor2saved, mutableSetOf())
        val course4 = Course(0, "c4", mutableSetOf(), tutor2saved, mutableSetOf())

        val course1Saved = courseService!!.register(course1)
        val course2Saved = courseService.register(course2)
        val course3Saved = courseService.register(course3)
        val course4Saved = courseService.register(course4)

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

        //val attendancesSFalseinDay2= atendances.map { AttendanceDTO.desdeModelo(it) }

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
                        true, 999
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
        // val studentregistered1 = studentService!!.register(student1)

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
        // val studentregistered2 = studentService.register(student2)

        val student3 =
                Student(
                        0, "Pedro",
                        "Picapiedra",
                        "456",
                        "pica@gmail.com",
                        firstattendaces,
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
                        "",
                        course2Saved,
                        false
                )
        //    val studentregistered4  =  studentService.register(student4)

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
        // val studentregistered5  =studentService.register(student5)

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
        //  val studentregistered6  = studentService.register(student6)

        val student7 =
                Student(
                        0,
                        "Alu3",
                        "Marmol",
                        "456",
                        "alu3@gmail.com",
                        firstattendaces,
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
                        "",
                        course2Saved,
                        false
                )
        // val studentregistered14  = studentService.register(student14)

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
        //  val studentregistered15  = studentService.register(student15)

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
        //  val studentregistered16  = studentService.register(student16)

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
        // val studentregistered17  =  studentService.register(student17)

        val student18 =
                Student(
                        0,
                        "Mrs",
                        "Spacely",
                        "456",
                        "spacely@gmail.com",
                        firstattendaces,
                        "",
                        course3Saved,
                        false
                )
        //  val studentregistered18 =  studentService.register(student18)
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
    fun Si_el_id_es_existente_Retorna_al_estudiante_asociado_con_ese_id_() {
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
    fun Si_el_id_es_existente_Actualiza_al_estudiante_asociado_con_ese_id_() {
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
    fun Si_ninguno_falto_ese_dia_en_particular_no_devuelve_estudiantes() {
        val day = 1
        studentService.register(student5)
        studentService.register(student6)
        studentService.register(student15)
        studentService.register(student16)
        studentService.register(student17)
        val absents = studentService.studentsAbsentAtAParticularDay(day)
        Assertions.assertTrue(absents.isEmpty())
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


    @AfterEach
    fun tearDown() {
        studentRepository.deleteAll()
        courseRepository.deleteAll()
        tutorRepository.deleteAll()
    }
}