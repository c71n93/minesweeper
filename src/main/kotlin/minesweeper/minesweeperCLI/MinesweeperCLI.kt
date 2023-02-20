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

    //fixme
    fun play() {
        _field.printCurrentHiddenField()
        do {
            requestSetOrDeleteMark()
            _field.printCurrentHiddenField()
        } while (true)
        println("Congratulations! You found all the mines!")
    }
//    fun play() {
//        _field.printCurrentHiddenField()
//        do {
//            requestSetOrDeleteMark()
//            _field.printCurrentHiddenField()
//        } while (!_field.isFieldMarkedCorrect())
//        println("Congratulations! You found all the mines!")
//    }

    private fun requestNumberOfMines(): Int {
        print("How many mines do you want on the field? ")
        return _scanner.nextInt()
    }
    private fun requestSetOrDeleteMark() {
        var isMarkSetOrDeleted: Boolean
        do {
            print("Set/delete mines marks (x and y coordinates): ")
            val col = _scanner.nextInt()
            val row = _scanner.nextInt()
            val markType = _scanner.nextLine()
            //fixme
            isMarkSetOrDeleted = true
            if (markType == "mine") {
                isMarkSetOrDeleted = _field.setOrDeleteMineMark(row - 1, col - 1)
            } else if (markType == "free") {
                isMarkSetOrDeleted = true
                _field.setOrDeleteFreeMark(row - 1, col - 1)
            }
        } while (!isMarkSetOrDeleted)
    }
}