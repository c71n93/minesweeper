package minesweeper.minesweeperCLI

import minesweeper.field.Field
import java.util.Scanner

class MinesweeperCLI(height: Int, width: Int) {
    private val _scanner = Scanner(System.`in`)
    private val _field: Field

    init {
        val numOfMines = requestNumberOfMines()
        _field = Field(height, width)
        _field.generateField(numOfMines)
    }

    fun play() {
        _field.printCloseField()
        do {
            requestSetOrDeleteMark()
            _field.printCloseField()
        } while (!_field.isFieldMarkedCorrect())
        println("Congratulations! You found all the mines!")
    }

    private fun requestNumberOfMines(): Int {
        print("How many mines do you want on the field? ")
        return _scanner.nextInt()
    }
    private fun requestSetOrDeleteMark() {
        var isMarkSetOrDeleted: Boolean
        do {
            print("Set/delete mines marks (x and y coordinates): ")
            val column = _scanner.nextInt()
            val row = _scanner.nextInt()
            isMarkSetOrDeleted = _field.setOrDeleteMark(row - 1, column - 1)
        } while (!isMarkSetOrDeleted)
    }
}