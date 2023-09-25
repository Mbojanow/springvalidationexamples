package com.example.validationworkshot

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.boot.CommandLineRunner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Entity
data class RoleEntity(
    @Id
    val id: UUID?,
    val name: String
) {
    constructor() : this(null, "")
}

interface RoleRepository: JpaRepository<RoleEntity, UUID>

@Component
class CLR(private val roleRepository: RoleRepository): CommandLineRunner {
    override fun run(vararg args: String?) {
        roleRepository.saveAll(
            listOf(
                RoleEntity(UUID.randomUUID(), "USER"),
                RoleEntity(UUID.randomUUID(), "ADMIN")
            ),
        )
    }
}