package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.Notifyer
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.persistence.AttendanceRepository
import ar.edu.unq.agiletutor.persistence.NotifyerRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class EmailServiceImpl {

    @Autowired
    private lateinit var notifyerRepository: NotifyerRepository

    @Autowired
    private lateinit var attendanceRepository: AttendanceRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var emailSender: JavaMailSender

    fun existsAny(): Boolean {
        return notifyerRepository.findAll().isNotEmpty()
    }

    @Transactional
    fun saveNotifyer(notifyer: Notifyer) {
        notifyerRepository.save(notifyer)
    }

    @Transactional
    fun getNotifyer(): Notifyer {
        if (!this.existsAny()) {
            val notifyer = Notifyer()
            this.saveNotifyer(notifyer)
        }
        return notifyerRepository.findAll()[0]!!
    }

    fun notifyAllAbsent(dayAttend: Int, courseId: Int) {
        val absentList = studentRepository.findAll().filter {
            it.course!!.id == courseId && !it.blocked
        }.filter {
            it.attendances.filter {
                it.day == dayAttend && !it.attended
            }.isNotEmpty()
        }
        this.addAbsent(absentList)
    }

    fun addAbsent(absentList: List<Student>) {
        val notifyer: Notifyer = getNotifyer()
        absentList.forEach { notifyer.addabsent(it) }
        this.saveNotifyer(notifyer)
    }

    fun changeSubjectText(text: String) {
        val notifyer: Notifyer = getNotifyer()
        notifyer.setSubjectEmail(text)
        this.saveNotifyer(notifyer)
    }

    fun changeBodyText(text: String) {
        val notifyer: Notifyer = getNotifyer()
        notifyer.setTextEmail(text)
        this.saveNotifyer(notifyer)
    }

    fun sendSimpleMessage(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setFrom(to)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }

    //    @Scheduled(cron = "*/5 * * * * *")//cada cinco segundos
    /*   fun pruebaCronJob() {
           println("adentro del cronJob de 5 segundos")
       }
   */

    @Transactional
    @Scheduled(cron = "\${cron.expressionat22hs}")//a las 22 horas
    //@Scheduled(cron = "\${cron.expression20seg}")//cada 20 segundos
    fun emailAbsent() {
        var notifyer: Notifyer = getNotifyer()
        println("todes" + notifyer.getabsent()!!.size)
        notifyer.getabsent()!!
            .forEach {
                this.sendSimpleMessage(
                    it.email!!,
                    notifyer.getSubjectEmail(),
                    notifyer.getTextEmail(it.name!!)
                )
            }
        notifyer.getabsent()!!

            .forEach {
                println(
                    it.email!! + "impresion" + notifyer.getSubjectEmail() + " " + notifyer.getTextEmail(
                        it.name!!
                    )
                )
            }

        notifyer.removeall()
        saveNotifyer(notifyer)
    }

    fun studentsToNotify(): List<StudentDTO> {
        val notifyer: Notifyer = getNotifyer()
        return notifyer.getabsent()!!.map { StudentDTO.desdeModelo(it) }
    }
}