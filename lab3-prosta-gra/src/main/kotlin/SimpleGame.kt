class SimpleGame(private val board: Board) {
    private var playerPosition = board.startPoint
    private var isOver = false

    fun play() {
        while (!isOver) {
            board.print()
            println("w - up, s - down, a - left, d - right, q - quit")
            print("Enter direction: ")
            val input = readlnOrNull()?.trim()?.lowercase()

            when (input) {
                "w" -> move(Direction.UP)
                "s" -> move(Direction.DOWN)
                "a" -> move(Direction.LEFT)
                "d" -> move(Direction.RIGHT)
                "q" -> isOver = true
                else -> println("Invalid input")
            }
        }
    }

    private fun move(direction: Direction) {
        val newPosition = when (direction) {
            Direction.UP -> Pair(playerPosition.first, playerPosition.second - 1)
            Direction.DOWN -> Pair(playerPosition.first, playerPosition.second + 1)
            Direction.LEFT -> Pair(playerPosition.first - 1, playerPosition.second)
            Direction.RIGHT -> Pair(playerPosition.first + 1, playerPosition.second)
        }

        if (board.isObstacle(newPosition)) {
            println("Obstacle!")
            return
        }

        if (newPosition == board.endPoint) {
            println("You won!")
            isOver = true
            return
        }

        board.updatePlayerPosition(playerPosition, newPosition)
        playerPosition = newPosition
    }
}
