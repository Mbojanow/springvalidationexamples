package com.example.validationworkshot

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.*
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

interface PersonUpdateValidationGroup

data class Nicknames(
    val nn: List<String>,

    @field:NotNull(message = "x cannot be null")
    val x: Int?
) {
    @AssertTrue
    fun isNicknamesNotBlank() =
        nn.all { it.isNotBlank() }
}

data class Person(

    @field:Valid
    val nicknames: Nicknames?,

    @field:Null(message = "id will be auto generated")
    @field:NotNull(message = "id needs to be provided", groups = [PersonUpdateValidationGroup::class])
    val id: Long?,

    @field:Size(min = 3, max = 15, message = "first name length incorrect")
    val firstName: String,

    @field:Size(min = 3, max = 15, message = "last name length incorrect")
    val lastName: String,

    @field:Size(min = 1, message = "roles cannot be empty")
    val roles: List<String>
) {

    @JsonIgnore
    @AssertTrue(message = "There is only one true Marian!")
    //@AssertFalse
    fun isTrueMarian(): Boolean =
        if (firstName == "Marian") {
            lastName == "Pohling"
        } else true

}

data class Person2(

    @field:Null(message = "id will be auto generated")
    @field:NotNull(message = "id needs to be provided", groups = [PersonUpdateValidationGroup::class])
    val id: Long?,

    @field:Size(min = 3, max = 15, message = "first name length incorrect")
    val firstName: String,

    @field:Size(min = 3, max = 15, message = "last name length incorrect")
    val lastName: String,

    @field:Size(min = 1, message = "roles cannot be empty")
    @field:RolesExist(message = "Boom2")
    val roles: List<String>
) {

    @JsonIgnore
    @AssertTrue(message = "There is only one true Marian!")
    //@AssertFalse
    fun isTrueMarian(): Boolean =
        if (firstName == "Marian") {
            lastName == "Pohling"
        } else true
}

data class Person3(

    @field:Null(message = "id will be auto generated")
    @field:NotNull(message = "id needs to be provided", groups = [PersonUpdateValidationGroup::class])
    val id: Long?,

    @field:Size(min = 3, max = 15, message = "first name length incorrect")
    val firstName: String,

    @field:Size(min = 3, max = 15, message = "last name length incorrect")
    val lastName: String,

    @field:Size(min = 1, message = "roles cannot be empty")
    @field:RolesExist(message = "Boom2")
    val roles: List<String>
) {

    @JsonIgnore
    @AssertTrue(message = "There is only one true Marian!")
    //@AssertFalse
    fun isTrueMarian(): Boolean =
        if (firstName == "Marian") {
            lastName == "Pohling"
        } else true
}


@Constraint(validatedBy = [RolesExistenceValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RolesExist(
    val message: String = "BOOM",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@Component
class RolesExistenceValidator(private val roleRepository: RoleRepository): ConstraintValidator<RolesExist, List<String>> {
    override fun isValid(value: List<String>?, context: ConstraintValidatorContext?): Boolean {
        val roleNames = roleRepository.findAll().map { it.name }
        return if (value == null) {
            true
        } else {
            roleNames.containsAll(value)
        }
    }
}
