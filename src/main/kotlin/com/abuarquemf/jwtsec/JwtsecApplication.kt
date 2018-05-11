package com.abuarquemf.jwtsec

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtsecApplication

fun main(args: Array<String>) {
    runApplication<JwtsecApplication>(*args)
}
