import kotlin.test.*

class BoardTest {

    @Test
    fun `board should initialize with correct dimensions`() {
        val width = 5
        val height = 5
        val board = Board(width, height)

        assertEquals(width + 2, board.board.size, "Board width includes borders")
        assertEquals(height + 2, board.board[0].size, "Board height includes borders")
    }

    @Test
    fun `start and end points should always be on edges`() {
        val board = Board(5, 5)
        val (startX, startY) = board.startPoint
        val (endX, endY) = board.endPoint

        // Check that start and end points are on the border
        assertTrue(startX == 1 || startX == 5 || startY == 1 || startY == 5, "Start point must be on the edge")
        assertTrue(endX == 1 || endX == 5 || endY == 1 || endY == 5, "End point must be on the edge")
    }

    @Test
    fun `start and end points should not overlap`() {
        val board = Board(5, 5)

        assertNotEquals(board.startPoint, board.endPoint, "Start and end points must be different")
    }

    @Test
    fun `board should generate obstacles`() {
        val board = Board(5, 5)
        val obstacleCount = board.board.flatten().count { it == 'X' }

        assertTrue(obstacleCount > 0, "Board should have obstacles")
    }

    @Test
    fun `should detect obstacle at position`() {
        val board = Board(5, 5)
        val position = board.startPoint // Start point is always on an edge
        assertFalse(board.isObstacle(position), "Start position should not be an obstacle")
    }

    @Test
    fun `should throw error for invalid dimensions`() {
        assertFailsWith<IllegalArgumentException> { Board(4, 5) }
        assertFailsWith<IllegalArgumentException> { Board(5, 4) }
    }

    @Test
    fun `update player position should reflect on the board`() {
        val board = Board(5, 5)
        val oldPosition = board.startPoint
        val newPosition = Pair(oldPosition.first + 1, oldPosition.second)

        board.updatePlayerPosition(oldPosition, newPosition)

        assertEquals('O', board.board[newPosition.second][newPosition.first], "New position should have player marker")
        assertEquals(' ', board.board[oldPosition.second][oldPosition.first], "Old position should be cleared")
    }


    @Test
    fun `board generates different configurations`() {
        val boards = List(5) { Board(5, 5).board }
        assertTrue(boards.distinct().size > 1, "Multiple boards should generate unique configurations")
    }
}
