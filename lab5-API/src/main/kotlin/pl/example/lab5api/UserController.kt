package pl.example.lab5api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsers(): ResponseEntity<List<User>> {
        val users = userService.getUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ResponseEntity<User> {
        val user = userService.getUser(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun addUser(@Valid @RequestBody userRequest: User): ResponseEntity<User> {
        val user = userService.addUser(userRequest)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @Valid @RequestBody userRequest: User,
        @PathVariable id: UUID
    ): ResponseEntity<User> {
        val user = userService.updateUser(id, userRequest)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}