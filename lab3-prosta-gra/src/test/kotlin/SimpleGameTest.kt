import io.mockk.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SimpleGameTest {

    @Test
    fun `game should quit gracefully`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.endPoint } returns Pair(5, 5)
        every { mockBoard.print() } just Runs

        val input = "q\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        val result = output.toString()
        assertFalse(result.contains("Invalid input"), "No invalid input should be detected")
        assertTrue(result.contains("Enter direction:"), "Game should ask for direction before quitting")

        verify(exactly = 0) { mockBoard.isObstacle(any()) }
    }


    @Test
    fun `player wins after reaching endpoint`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.endPoint } returns Pair(1, 2)
        every { mockBoard.isObstacle(any()) } returns false

        val input = "s\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        assertTrue(output.toString().contains("You won!"), "Winning condition should be displayed")
    }

    @Test
    fun `player cannot move into obstacle`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.isObstacle(any()) } returns true

        val input = "w\nq\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        verify(exactly = 0) { mockBoard.updatePlayerPosition(any(), any()) }
        assertTrue(output.toString().contains("Obstacle!"), "Player should not move into obstacle")
    }


    @Test
    fun `multiple valid moves update position`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.isObstacle(any()) } returns false

        val input = "d\nd\ns\nq\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        verify {
            mockBoard.updatePlayerPosition(Pair(1, 1), Pair(2, 1))
            mockBoard.updatePlayerPosition(Pair(2, 1), Pair(3, 1))
            mockBoard.updatePlayerPosition(Pair(3, 1), Pair(3, 2))
        }
    }


    @Test
    fun `game should detect invalid input`() {
        val mockBoard = mockk<Board>(relaxed = true)

        val input = "x\nq\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        val result = output.toString()
        assertTrue(result.contains("Invalid input"), "Invalid input should be detected")
    }


    @Test
    fun `game should handle edge boundary moves`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.endPoint } returns Pair(5, 5)
        every { mockBoard.isObstacle(ofType<Pair<Int, Int>>()) } answers {
            val position = firstArg<Pair<Int, Int>>()
            position == Pair(1, 0)
        }

        val input = "w\nq\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        val result = output.toString()
        assertTrue(result.contains("Obstacle!"), "Boundary should be treated as an obstacle")
        verify { mockBoard.isObstacle(Pair(1, 0)) }
    }


    @Test
    fun `game should detect winning condition`() {
        val mockBoard = mockk<Board>(relaxed = true)
        every { mockBoard.startPoint } returns Pair(1, 1)
        every { mockBoard.endPoint } returns Pair(1, 2)
        every { mockBoard.isObstacle(any()) } returns false

        val input = "s\n".byteInputStream()
        val output = ByteArrayOutputStream()
        System.setIn(input)
        System.setOut(PrintStream(output))

        val game = SimpleGame(mockBoard)
        game.play()

        val result = output.toString()
        assertTrue(result.contains("You won!"), "Winning condition should be displayed")

        verify { mockBoard.isObstacle(Pair(1, 2)) }
        verify(exactly = 0) { mockBoard.updatePlayerPosition(any(), any()) }
    }

}
