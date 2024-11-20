package pl.example

import pl.example.Direction.*


class SimpleGame(width: Int, height: Int) {
    private val board = Board(width, height)
    private var playerPosition = board.startPoint
    private var isOver = false

    fun play() {
        while (!isOver) {
            board.print()
            println("w - up, s - down, a - left, d - right,q - quit")
            print("Enter direction: ")
            val input = readlnOrNull()!![0]
            print("\n\n\n")

            when (input) {
                'w' -> move(UP)
                's' -> move(DOWN)
                'a' -> move(LEFT)
                'd' -> move(RIGHT)
                'q' -> isOver = true
                else -> println("Invalid input")
            }
        }
    }

    fun currentPlayerPosition(): Pair<Int, Int> {
        return playerPosition
    }

    private fun move(direction: Direction) {
        val newPosition = when (direction) {
            UP -> Pair(playerPosition.first, playerPosition.second - 1)
            DOWN -> Pair(playerPosition.first, playerPosition.second + 1)
            LEFT -> Pair(playerPosition.first - 1, playerPosition.second)
            RIGHT -> Pair(playerPosition.first + 1, playerPosition.second)
        }

        if (board.isObstacle(newPosition)) {
            println("Obstacle!")
            return
        }

        if (newPosition == board.endPoint) {
            println("You won!")
            isOver = true
        }
        board.updatePlayerPosition(playerPosition, newPosition)
        playerPosition = newPosition
    }
}