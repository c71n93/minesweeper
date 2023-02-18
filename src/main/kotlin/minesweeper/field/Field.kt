package minesweeper.field

import kotlin.random.Random

private const val SAFE_CELL = '.'
private const val MINE_CELL = 'X'

class Field(private val height: Int, private val width: Int) {
    private val field = MutableList(height) { MutableList(width) { SAFE_CELL } }

    // took this solution from here: https://hyperskill.org/projects/8/stages/47/implement#solutions-309746
    fun generateField(numOfMines: Int) {
        var restMines = numOfMines
        var restCells = height * width
        for (i in 0 .. field.lastIndex) {
            for (j in 0 .. field[i].lastIndex) {
                if (Random.nextInt(0, restCells) < restMines) {
                    placeMine(i, j)
                    restMines--
                }
                restCells--
            }
        }
    }
    fun printField() {
        for (i in 0..field.lastIndex) {
            for (j in field[i]) {
                print(j.toString())
            }
            println()
        }
    }

    // took this solution from here: https://hyperskill.org/projects/8/stages/48/implement#solutions-190159
    private fun placeMine(row: Int, column: Int) {
        field[row][column] = MINE_CELL
        for (r in maxOf(row - 1, 0)..minOf(row + 1, height - 1)) {
            for (c in maxOf(column - 1, 0)..minOf(column + 1, width - 1)) {
                when (field[r][c]) {
                    SAFE_CELL -> field[r][c] = '1'
                    in '1'..'7' -> field[r][c]++
                }

            }
        }
    }
}