package ar.edu.unq.agiletutor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AgiletutorApplication

fun main(args: Array<String>) {
    runApplication<AgiletutorApplication>(*args)
}
