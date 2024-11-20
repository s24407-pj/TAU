
import pl.example.SimpleGame
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SimpleGameTest {


    @Test
    fun `game should quit gracefully`() {
        val input = "q\n".byteInputStream() // Symulujemy tylko wyjście
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(5, 5)
        game.play()
        


        val result = output.toString()
        assertFalse(result.contains("Invalid input"), "Nie powinno być nieprawidłowego wejścia.")
        assertTrue(result.contains("Enter direction:"), "Gra powinna oczekiwać kierunku.")
    }

    @Test
    fun `game should detect invalid input`() {
        val input = "x\nq\n".byteInputStream() // Symulujemy nieprawidłowy ruch, a potem wyjście
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(5, 5)
        game.play()

        val result = output.toString()
        assertTrue(result.contains("Invalid input"), "Gra powinna informować o nieprawidłowym wejściu.")
    }
}
