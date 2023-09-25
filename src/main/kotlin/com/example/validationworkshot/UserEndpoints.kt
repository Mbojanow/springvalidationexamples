package com.example.validationworkshot

import jakarta.validation.Valid
import jakarta.validation.Validator
import jakarta.validation.groups.Default
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserEndpoints(private val userService: UserService,
    private val converters: HttpMessageConverters) {

    @PostMapping(consumes = ["application/json", "application/prodaso.ai.v1+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid person: Person): Person {
        return userService.create(person)
    }

    @PostMapping(consumes = ["application/prodaso.ai.v2+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUserV2(@RequestBody @Valid person: Person2): Person2 {
        return userService.create(person)
    }

    @PostMapping(consumes = ["application/prodaso.ai.v3+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUserV2(@RequestBody person: Person3): Person3 {
        // mapping person3 to some other object
        return userService.create(person)
    }

    @PutMapping
    fun updateUser(@RequestBody @Validated(PersonUpdateValidationGroup::class, Default::class) person: Person): Person {
        return userService.create(person)
    }
}

@Service
@Validated
class UserService(private val validator: Validator) {

    fun create(person: Person): Person {
        return person
    }

    fun create(person: Person2): Person2 {
        return person
    }

    fun create( person: Person3): Person3 {
        //validate(person) DOES NOT WORK
        val errors = validator.validate(person, Default::class.java)
        if (errors.isNotEmpty()) {
            throw RuntimeException("Dhiorehiuwte")
        }
        return person
    }

    fun validate(@Valid person3: Person3) {}
}