package pl.example.lab5api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserApiTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val userRepository: UserRepository,
    @Autowired val objectMapper: ObjectMapper,

    ) {

    val user1 = User(
        name = "John",
        email = "email@test.com"
    )

    val user2 = User(
        name = "Alice",
        email = "email@email.com"
    )

    val user3 = User(
        name = "Mark",
        email = "mark@email.com"
    )

    @BeforeEach
    fun beforeEach() {
        userRepository.deleteAll()
    }

    @Test
    fun `should get all users`() {
        userRepository.saveAll(listOf(user1, user2, user3))
        mockMvc.get("/users")
            .andExpect {
                status { isOk() }
                jsonPath("$") { isArray() }
                jsonPath("$.size()") { value(3) }
            }
            .andReturn()
    }

    @Test
    fun `should get user by id`() {
        val user = userRepository.save(user1)
        mockMvc.get("/users/${user.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("John") }
            }
            .andReturn()
    }

    @Test
    fun `should throw 404 when user not found`() {
        mockMvc.get("/users/${UUID.randomUUID()}")
            .andExpect {
                status { isNotFound() }
            }
            .andReturn()
    }

    @Test
    fun `should add user`() {
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(user1)
        }
            .andExpect {
                status { isCreated() }
                jsonPath("$.name") { value("John") }
            }
            .andReturn()
    }

    @Test
    fun `should throw 400 when email is not valid`() {
        val user = user1.copy(email = "invalid")
        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(user)
        }
            .andExpect {
                status { isBadRequest() }
            }
            .andReturn()
    }

    @Test
    fun `should update user`() {
        val user = userRepository.save(user1)
        val updatedUser = user1.copy(name = "Updated")
        mockMvc.put("/users/${user.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedUser)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("Updated") }
            }
            .andReturn()
    }

    @Test
    fun `should return updated user`() {
        val user = userRepository.save(user1)
        val updatedUser = user1.copy(name = "Updated")
        mockMvc.put("/users/${user.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedUser)
        }
            .andExpect {
                status { isOk() }
            }
            .andReturn()

        mockMvc.get("/users/${user.id}") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("Updated") }
            }
            .andReturn()
    }


    @Test
    fun `should throw 404 when user not found while updating`() {
        mockMvc.put("/users/${UUID.randomUUID()}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(user1)
        }
            .andExpect {
                status { isNotFound() }
            }
            .andReturn()
    }

    @Test
    fun `should delete user`() {
        val user = userRepository.save(user1)
        mockMvc.delete("/users/${user.id}")
            .andExpect {
                status { isNoContent() }
            }
            .andReturn()
    }

    @Test
    fun `should throw 404 when user deleted`() {
        val user = userRepository.save(user1)
        mockMvc.delete("/users/${user.id}")
            .andExpect {
                status { isNoContent() }
            }
            .andReturn()

        mockMvc.delete("/users/${user.id}")
            .andExpect {
                status { isNotFound() }
            }
            .andReturn()
    }
}