package chaegang.pl_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PlApiApplication

fun main(args: Array<String>) {
    runApplication<PlApiApplication>(*args)
}
