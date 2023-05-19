package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.TutorRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException



@SpringBootTest
internal class TutorServiceTest {
    @Autowired
    lateinit var   tutorService: TutorService
    @Autowired
    lateinit var  tutorRepository: TutorRepository


    var  tutors =listOf<Tutor>()

    lateinit var tutor1: Tutor
    lateinit var tutor2: Tutor
    lateinit var tutorData: TutorRegisterDTO
    lateinit var tutor3: Tutor


    @BeforeEach
    fun setUp() {

        tutor1 = Tutor(0,"Alejandro","Fariña","ale@gmail.com", "passtut1",mutableSetOf())
        tutor2 = Tutor(0,"Cristian","Gonzalez","cris@gmail.com", "passtut2",mutableSetOf())
        tutorData =  TutorRegisterDTO (0,"OtroAlejandro","OtroFariña","otromaildeale@gmail.com","otropassdeale")
        tutor3 = Tutor(0,"Cristian","Gonzalez","otromaildeale@gmail.com", "passtut2",mutableSetOf())
    }

    /**get  Tutors*/
    @Test
    fun al_solicitar_a_una_DB_sin_tutores_no_devuelve_ninguno() {
        tutors = tutorService.findAll()

        assertTrue ( tutors.isEmpty())
    }

    @Test
    fun al_solicitar_a_La_DB_todos_los_tutores_Devuelve_la_cantidad_de_tutores_registrados() {
        tutorService.register(tutor1)
        tutorService.register(tutor3)
        tutors = tutorService.findAll()
        assertTrue( tutors.isNotEmpty() )
        assertEquals(2, tutors.size)
    }

    /**Register a tutor */

    @Test
    fun el_tutor_registrado_mantiene_las_mismas_propiedades_luego_de_registrarse() {
        val tutor1registered = tutorService.register(tutor1)
        assertEquals(tutor1.name, tutor1registered.name)
        assertEquals(tutor1.surname, tutor1registered.surname)
        assertEquals(tutor1.email, tutor1registered.email)
        assertEquals(tutor1.password, tutor1registered.password)


    }


    @Test
    fun al_Registrar_un_tutor_se_incrementa_la_cantidad_en_una_unidad() {
        tutorService.register(tutor1)
        val tutors = tutorService.findAll()
        assertEquals(1, tutors.size)
    }

    @Test
    fun al_Intentar_Registrar_un_usuario_con_un_mail_ya_existente_lanza_excepcion() {
        tutorService.register(tutor1)
        assertThrows<UsernameExistException> {  tutorService.register(tutor1) }
    }


    /**loggin a tutor*/
    @Test
    fun al_intentar_loguearse_un_tutor_no_registrado_Lanza_excepcion() {

        assertThrows<ItemNotFoundException> {  tutorService.login( "tutor1@gmail.com", "passtut1") }
    }

    @Test
    fun un_tutor_puede_loguearse_si_esta_registrado() {
        val registered= tutorService.register(tutor1)
        val logged= tutorService.login( registered.email!!, registered.password!!)
        assertEquals(registered.id,  logged.id)
        assertEquals(registered.name,  logged.name)
        assertEquals(registered.surname, logged.surname)
        assertEquals(registered.email,  logged.email)
        assertEquals(registered.password, logged.password)

    }


    /** find a tutor By Id */
    @Test
    fun al_intentar_buscar_un_tutor_con_id_no_existente_Lanza_excepcion() {
        tutorService.register(tutor1)
        assertThrows<ItemNotFoundException> {  tutorService.findByID( 0) }
    }


    @Test
    fun Si_el_id_es_existente_Retorna_al_tutor_asociado_con_ese_id_() {
        val tutorRegistered =tutorService.register(tutor1)
        val userFound = tutorService.findByID(tutorRegistered.id!!)
        assertEquals( tutorRegistered.id, userFound.id)
        assertEquals( tutorRegistered.name, userFound.name)
        assertEquals( tutorRegistered.surname, userFound.surname)
        assertEquals( tutorRegistered.email, userFound.email)
        assertEquals( tutorRegistered.password, userFound.password)

    }


    /** update a tutor* */
    @Test
    fun al_intentar_actualizar_un_tutor_con_id_no_existente_Lanza_excepcion() {
        tutorService.register(tutor1)
        assertThrows<ItemNotFoundException> {  tutorService.update(0, tutorData)}
    }

    @Test
    fun Al_Intentar_Actualizar_Si_el_mail_es_existente_lanza_excepcion() {
        val tutorRegistered = tutorService.register(tutor1)
        tutorService.register(tutor3)

        assertThrows<DataIntegrityViolationException> {  tutorService.update(tutorRegistered.id!!, tutorData) }

    }


    @Test
    fun Si_el_id_es_existente_Actualiza_el_usuario_asociado_con_ese_id_() {
        val tutorRegistered = tutorService.register(tutor1)
        val updated = tutorService.update(tutorRegistered.id!!, tutorData)
        val restored= tutorService.findByID(tutorRegistered.id!!)
       assertEquals( updated.id, restored.id)
        assertEquals( updated.name, restored.name)
        assertEquals( updated.surname, restored.surname)
        assertEquals( updated.email, restored.email)
        assertEquals( updated.password, restored.password)

    }


    /** Delete By Id */
    @Test
    fun al_intentar_borrar_un_tutor_con_id_no_existente_lanza_excepcionm_y_La_DB_se_mantiene_sin_alterar() {
        tutorService.register(tutor1)
        val tutors = tutorService.findAll()

        assertThrows<ItemNotFoundException> {  tutorService.deleteById(0)}

        assertTrue( tutors.isNotEmpty() )
    }

    @Test
    fun Si_el_id_es_existente_el_tutor_asociado_con_ese_id_es_eliminado_() {
        val tutorRegistered = tutorService.register(tutor1)
        tutorService.deleteById(tutorRegistered .id!!)
        val tutors = tutorService.findAll()

        assertTrue( tutors.isEmpty() )
    }


    @AfterEach
    fun tearDown() {
      tutorRepository.deleteAll()
    }

}