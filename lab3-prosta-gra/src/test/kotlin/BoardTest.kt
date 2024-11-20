import pl.example.Board
import kotlin.test.*

class BoardTest {

    @Test
    fun `board should initialize with correct dimensions`() {
        val width = 5
        val height = 5
        val board = Board(width, height)

        assertEquals(width + 2, board.board.size, "Board width includes borders")
        assertEquals(height + 2, board.board.size, "Board height includes borders")
    }

    @Test
    fun `board should generate start and end points`() {
        val board = Board(5, 5)

        assertNotNull(board.startPoint, "Start point is initialized")
        assertNotNull(board.endPoint, "End point is initialized")
        assertNotEquals(board.startPoint, board.endPoint, "Start and end points should not be the same")
    }

    @Test
    fun `start and end points should not be adjacent`() {
        val board = Board(5, 5)
        val areAdjacent = board.areAdjacent(board.startPoint, board.endPoint)

        assertFalse(areAdjacent, "Start and end points should not be adjacent")
    }

    @Test
    fun `board should generate obstacles`() {
        val board = Board(5, 5)
        val obstacles = board.board.flatten().filter { it == 'X' }

        assertTrue(obstacles.size > 24, "Obstacles are generated")
    }

    @Test
    fun `should throw when width or height is less than 5`() {
        assertFailsWith<IllegalArgumentException> {
            Board(4, 5)
        }
        assertFailsWith<IllegalArgumentException> {
            Board(5, 4)
        }
    }

    @Test
    fun `should print board`() {
        val board = Board(5, 5)
        board.print()
    }

    @Test
    fun `should check if position is obstacle`() {
        val board = Board(5, 5)
        val isObstacle = board.isObstacle(Pair(0, 0))
        assertTrue(isObstacle, "Position is an obstacle")
    }
}
