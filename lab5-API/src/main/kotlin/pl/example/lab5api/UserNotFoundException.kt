package pl.example.lab5api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(id: UUID) : RuntimeException("User with id $id not found") {

}
