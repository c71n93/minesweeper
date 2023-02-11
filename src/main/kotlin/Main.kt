package minesweeper

import java.util.Scanner
import kotlin.random.Random

const val FIELD_SIZE = 9
fun main() {
    val field = MutableList(FIELD_SIZE) { MutableList(FIELD_SIZE) { '.' } }
    val numOfMines = requestNumberOfMines()
    val minesCoordinates = getMineCoordinates(numOfMines)

    for (i in 0 .. minesCoordinates.lastIndex) {
        val x = minesCoordinates[i] / FIELD_SIZE
        val y = minesCoordinates[i] % FIELD_SIZE
        field[x][y] = 'X'
    }

    for (i in 0..field.lastIndex) {
        for (j in field[i]) {
            print(j.toString())
        }
        println()
    }
}

fun getMineCoordinates(numOfMines: Int): MutableList<Int> {
    val minesCoordinates = MutableList(numOfMines) { -1 }
    for (i in 0 until numOfMines) {
        var randomCoordinate: Int
        /* TODO: this is very stupid decision, because on the last iteration
             there is a small chance to get suitable random number, so this
             loop will make a lot of iterations until it got suitable number */
        do {
            randomCoordinate = Random.nextInt(0, FIELD_SIZE * FIELD_SIZE)
        } while (randomCoordinate in minesCoordinates)
        minesCoordinates[i] = randomCoordinate
    }
    return minesCoordinates
}

fun requestNumberOfMines(): Int {
    print("How many mines do you want on the field?")
    val scanner = Scanner(System.`in`)
    return scanner.nextInt()
}