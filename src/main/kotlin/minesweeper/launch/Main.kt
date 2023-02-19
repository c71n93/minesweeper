package minesweeper.launch

import java.util.Scanner
import minesweeper.field.Field

const val FIELD_HEIGHT = 9
const val FIELD_WIDTH = 9
fun main() {
    val field = Field(FIELD_HEIGHT, FIELD_WIDTH)
    val numOfMines = requestNumberOfMines()
    field.generateField(numOfMines)
    field.printCloseField()
    while (true) {
        requestSetOrDeleteMark(field)
        field.printCloseField()
    }
}

fun requestNumberOfMines(): Int {
    print("How many mines do you want on the field? ")
    val scanner = Scanner(System.`in`)
    return scanner.nextInt()
}

fun requestSetOrDeleteMark(field: Field) {
    print("Set/delete mines marks (x and y coordinates): ")
    val scanner = Scanner(System.`in`)
    var isMarkSetOrDeleted = false
    while (!isMarkSetOrDeleted) {
        val column = scanner.nextInt()
        val row = scanner.nextInt()
        isMarkSetOrDeleted = field.setOrDeleteMark(row - 1, column - 1)
    }
}