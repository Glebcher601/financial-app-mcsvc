package com.nixsolutions.uiservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class UiServiceApplication

fun main(args: Array<String>) {
    runApplication<UiServiceApplication>(*args)
}
