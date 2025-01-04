package pl.example.lab5api

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {
    fun getUsers(): List<User> =
        userRepository.findAll()

    fun getUser(id: UUID): User =
        userRepository.findById(id).orElseThrow { UserNotFoundException(id) }

    fun addUser(user: User): User =
        userRepository.save(user)

    fun updateUser(id: UUID,user: User): User {
        userRepository.findById(id).orElseThrow { UserNotFoundException(id) }
        return userRepository.save(user)
    }

    fun deleteUser(id: UUID) {
        userRepository.findById(id).orElseThrow { UserNotFoundException(id) }
        userRepository.deleteById(id)
    }
}