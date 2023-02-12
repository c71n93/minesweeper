package minesweeper

import java.util.Scanner
import kotlin.random.Random

const val FIELD_HEIGHT = 9
const val FIELD_WIDTH = 9

class Field(private val height: Int, private val width: Int) {
    private val field = MutableList(height) { MutableList(width) { '.' } }

    fun placeMines(numOfMines: Int) {
        val minesCoordinates = generateMineCoordinates(numOfMines)
        for (i in 0 .. minesCoordinates.lastIndex) {
            val row = minesCoordinates[i] / width
            val column = minesCoordinates[i] % width
            field[row][column] = 'X'
        }
    }
    /* TODO: very slow method */
    fun printField() {
        for (i in 0..field.lastIndex) {
            for (j in field[i]) {
                print(j.toString())
            }
            println()
        }
    }
    private fun generateMineCoordinates(numOfMines: Int): MutableList<Int> {
        val minesCoordinates = MutableList(numOfMines) { -1 }
        for (i in 0 until numOfMines) {
            var randomCoordinate: Int
            /* TODO: this is very stupid decision, because on the last iteration
                 there is a small chance to get suitable random number, so this
                 loop will make a lot of iterations until it finally got it */
            do {
                randomCoordinate = Random.nextInt(0, width * height)
            } while (randomCoordinate in minesCoordinates)
            minesCoordinates[i] = randomCoordinate
        }
        return minesCoordinates
    }
}
fun main() {
    val field = Field(FIELD_HEIGHT, FIELD_WIDTH)
    val numberOfMines = requestNumberOfMines()
    field.placeMines(numberOfMines)
    field.printField()
}

fun requestNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    val scanner = Scanner(System.`in`)
    return scanner.nextInt()
}