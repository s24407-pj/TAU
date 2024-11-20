package pl.example

import kotlin.math.abs
import kotlin.random.Random

class Board(private val width: Int, private val height: Int) {
    val board = generateBoard()
    lateinit var startPoint: Pair<Int, Int>
    lateinit var endPoint: Pair<Int, Int>

    init {
        require(width > 4 && height > 4) { "Width and height must be greater than 4" }
    }

    fun print() {
        val builder = StringBuilder()
        for (row in board) {
            builder.append(row.joinToString(" ")).append("\n")
        }
        println(builder)
    }

    private fun generateBoard(): Array<Array<Char>> {
        val board = Array(height + 2) { Array(width + 2) { ' ' } }
        addBorder(board)
        initializeStartAndEndPoints(board)
        generateObstacles(board)
        return board
    }

    private fun generateObstacles(board: Array<Array<Char>>) {
        for (i in 1..height) {
            for (j in 1..width) {
                if (Random.nextDouble() < 0.35 && board[i][j] != 'A' && board[i][j] != 'B') {
                    board[i][j] = 'X'
                }
            }
        }
    }

    private fun initializeStartAndEndPoints(board: Array<Array<Char>>) {
        startPoint = generateRandomEdgePoint()

        do {
            endPoint = generateRandomEdgePoint()
        } while (areAdjacent(startPoint, endPoint))

        board[startPoint.second][startPoint.first] = 'A' //pytanie czy dobrze
        board[endPoint.second][endPoint.first] = 'B'
    }

    private fun addBorder(board: Array<Array<Char>>) {
        for (i in 0..height + 1) {
            for (j in 0..width + 1) {
                if (i == 0 || i == height + 1 || j == 0 || j == width + 1) {
                    board[i][j] = 'X'
                }
            }
        }
    }

    private fun generateRandomEdgePoint(): Pair<Int, Int> {
        return when ((0..3).random()) {
            0 -> Pair((1 .. width ).random(), 1) // Top edge
            1 -> Pair((1 .. width ).random(), height) // Bottom edge
            2 -> Pair(1, (1 .. height).random()) // Left edge
            else -> Pair(width, (1 .. height).random()) // Right edge
        }
    }

    fun areAdjacent(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
        val dx = abs(p1.first - p2.first)
        val dy = abs(p1.second - p2.second)
        return dx + dy <= 1
    }

    fun isObstacle(newPosition: Pair<Int, Int>): Boolean {
        return board[newPosition.second][newPosition.first] == 'X'
    }

    fun updatePlayerPosition(playerPosition: Pair<Int, Int>, newPosition: Pair<Int, Int>) {
        board[playerPosition.second][playerPosition.first] = ' '
        board[newPosition.second][newPosition.first] = 'O'
    }
}


