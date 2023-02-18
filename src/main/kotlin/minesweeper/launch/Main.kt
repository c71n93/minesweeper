package minesweeper.launch

import java.util.Scanner
import minesweeper.field.Field

const val FIELD_HEIGHT = 9
const val FIELD_WIDTH = 9
fun main() {
    val field = Field(FIELD_HEIGHT, FIELD_WIDTH)
    val numOfMines = requestNumberOfMines()
    field.generateField(numOfMines)
    field.printField()
}

fun requestNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    val scanner = Scanner(System.`in`)
    return scanner.nextInt()
}