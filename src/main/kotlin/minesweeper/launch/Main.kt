package minesweeper

import java.util.Scanner
import kotlin.random.Random

const val FIELD_HEIGHT = 9
const val FIELD_WIDTH = 9

class Field(private val height: Int, private val width: Int) {
    private val field = MutableList(height) { MutableList(width) { "." } }

    enum class CornerType {
        TOP_LEFT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_RIGHT
    }

    enum class SideType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    /* fixme: Is it efficient to check all cells? It definitely
        will be inefficient if there is, for example, only 1 mine */
    fun placeMines(numOfMines: Int) {
        val minesCoordinates = generateMineCoordinates(numOfMines)
        for (i in 0 .. minesCoordinates.lastIndex) {
            val row = minesCoordinates[i] / width
            val column = minesCoordinates[i] % width
            field[row][column] = "X"
        }
    }
    fun countAllMines() {
        for (i in 0..field.lastIndex) {
            for (j in 0 .. field[i].lastIndex) {
                if (field[i][j] != "X") {
                    val numOfMinesAround = countMinesSingleCell(i, j)
                    field[i][j] = if(numOfMinesAround != 0) numOfMinesAround.toString() else "."
                }
            }
        }
    }
    fun printField() {
        for (i in 0..field.lastIndex) {
            for (j in field[i]) {
                print(j)
            }
            println()
        }
    }
    /* fixme: very slow method */
    private fun generateMineCoordinates(numOfMines: Int): MutableList<Int> {
        val minesCoordinates = MutableList(numOfMines) { -1 }
        for (i in 0 until numOfMines) {
            var randomCoordinate: Int
            /* fixme: this is very stupid decision, because on the last iteration
                 there is a small chance to get suitable random number, so this
                 loop will make a lot of iterations until it finally got it */
            do {
                randomCoordinate = Random.nextInt(0, width * height)
            } while (randomCoordinate in minesCoordinates)
            minesCoordinates[i] = randomCoordinate
        }
        return minesCoordinates
    }
    private fun countMinesSingleCell(row: Int, column: Int) = when {
        row == 0 && column == 0 -> countMinesCorner(row, column, CornerType.TOP_LEFT)
        row == height - 1 && column == 0 -> countMinesCorner(row, column, CornerType.BOTTOM_LEFT)
        row == height - 1 && column == width - 1 -> countMinesCorner(row, column, CornerType.BOTTOM_RIGHT)
        row == 0 && column == width - 1 -> countMinesCorner(row, column, CornerType.TOP_RIGHT)

        row == 0 && (column != 0 || column != width - 1) -> countMinesSide(row, column, SideType.TOP)
        row == height - 1 && (column != 0 || column != width - 1) -> countMinesSide(row, column, SideType.BOTTOM)
        (row != 0 || row != height - 1) && column == 0 -> countMinesSide(row, column, SideType.LEFT)
        (row != 0 || row != height - 1) && column == width - 1 -> countMinesSide(row, column, SideType.RIGHT)

        else -> countMinesMiddle(row, column)
    }
    private fun countMinesCorner(row: Int, column: Int, cornerType: CornerType): Int {
        val rowShift: Int
        val columnShift: Int
        when (cornerType) {
            CornerType.TOP_LEFT -> {rowShift = 1; columnShift = 1}
            CornerType.BOTTOM_LEFT -> {rowShift = -1; columnShift = 1}
            CornerType.BOTTOM_RIGHT -> {rowShift = -1; columnShift = -1}
            CornerType.TOP_RIGHT -> {rowShift = 1; columnShift = -1}
        }

        var numOfMinesAround = 0
        numOfMinesAround += if (field[row][column + columnShift] == "X") 1 else 0
        numOfMinesAround += if (field[row + rowShift][column + columnShift] == "X") 1 else 0
        numOfMinesAround += if (field[row + rowShift][column] == "X") 1 else 0
        return numOfMinesAround
    }
    private fun countMinesSide(row: Int, column: Int, sideType: SideType): Int {
        var numOfMinesAround = 0
        if (sideType == SideType.TOP || sideType == SideType.BOTTOM) {
            val rowShift = when(sideType) {
                SideType.TOP -> 1
                else -> -1
            }
            numOfMinesAround += if (field[row][column - 1] == "X") 1 else 0
            numOfMinesAround += if (field[row + rowShift][column - 1] == "X") 1 else 0
            numOfMinesAround += if (field[row + rowShift][column] == "X") 1 else 0
            numOfMinesAround += if (field[row + rowShift][column + 1] == "X") 1 else 0
            numOfMinesAround += if (field[row][column + 1] == "X") 1 else 0
        } else {
            val columnShift = when(sideType) {
                SideType.LEFT -> 1
                else -> -1
            }
            numOfMinesAround += if (field[row + 1][column] == "X") 1 else 0
            numOfMinesAround += if (field[row + 1][column + columnShift] == "X") 1 else 0
            numOfMinesAround += if (field[row][column + columnShift] == "X") 1 else 0
            numOfMinesAround += if (field[row - 1][column + columnShift] == "X") 1 else 0
            numOfMinesAround += if (field[row - 1][column] == "X") 1 else 0
        }
        return numOfMinesAround
    }
    private fun countMinesMiddle(row: Int, column: Int): Int {
        var numOfMinesAround = 0
        for (i in -1 .. 1) {
            for (j in -1 .. 1) {
                if (!(i == 0 && j == 0)) {
                    numOfMinesAround += if (field[row + i][column + j] == "X") 1 else 0
                }
            }
        }
        return numOfMinesAround
    }
}
fun main() {
    val field = Field(FIELD_HEIGHT, FIELD_WIDTH)
    val numOfMines = requestNumberOfMines()
    field.placeMines(numOfMines)
    field.countAllMines()
    field.printField()
}

fun requestNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    val scanner = Scanner(System.`in`)
    return scanner.nextInt()
}