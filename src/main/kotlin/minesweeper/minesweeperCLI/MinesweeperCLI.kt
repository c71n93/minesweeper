package minesweeper.minesweeperCLI

import minesweeper.field.Field
import minesweeper.field.GameStatus
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
        _field.printCurrentHiddenField()
        do {
            requestSetOrDeleteMark()
            if (_field.fieldStatus != GameStatus.DEFEAT) {
                _field.printCurrentHiddenField()
            }
        } while (_field.fieldStatus == GameStatus.GAME)
        if (_field.fieldStatus == GameStatus.DEFEAT) {
            _field.printExplodedField()
            println("You stepped on a mine and failed!")
        } else if (_field.fieldStatus == GameStatus.VICTORY){
            println("Congratulations! You found all the mines!")
        }

    }

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
            val markType = _scanner.next()
            //fixme: check if markType is not "mine" or "free"
            isMarkSetOrDeleted = if (markType == "mine") {
                _field.setOrDeleteMineMark(row - 1, col - 1)
            } else {
                _field.setFreeMark(row - 1, col - 1)
            }
        } while (!isMarkSetOrDeleted)
    }
}