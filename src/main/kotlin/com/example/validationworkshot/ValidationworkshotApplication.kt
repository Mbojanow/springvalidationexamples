package com.example.validationworkshot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ValidationworkshotApplication

fun main(args: Array<String>) {
    runApplication<ValidationworkshotApplication>(*args)
}
