package ar.edu.unq.agiletutor.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component


@Component
class EmailServiceImpl {


    @Autowired
    private lateinit var emailSender: JavaMailSender

    fun sendSimpleMessage(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setFrom(to)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }
}