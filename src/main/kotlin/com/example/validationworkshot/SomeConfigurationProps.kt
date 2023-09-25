package com.example.validationworkshot

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "some.prodaso")
@Validated
data class SomeConfigurationProps(

    @field:NotBlank(message = "a cannot be blank")
    val a: String,
    @field:Min(3)
    val b: Int,

    @NestedConfigurationProperty
    val inner: InnerProps
)

data class InnerProps(
    @field:Min(2)
    val c: Int
)