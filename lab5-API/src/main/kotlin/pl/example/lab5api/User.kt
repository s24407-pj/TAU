package pl.example.lab5api

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,
    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,
)
