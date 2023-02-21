package minesweeper.field

import kotlin.random.Random

//TODO: figure out how to write enum class, that can contain cell content ('.', 'X', '1' .. '7)
const val SAFE_CELL = '.'
const val MINE_CELL = 'X'

const val MARKED_MINE_CELL = '*'
const val MARKED_FREE_CELL = '/'

enum class CellStatus {
    UNKNOWN,
    MARKED_MINE,
    MARKED_FREE
}

enum class GameStatus {
    GAME,
    DEFEAT,
    VICTORY
}

class Cell(var content: Char = SAFE_CELL, var status: CellStatus = CellStatus.UNKNOWN) {
    fun printOpen() {
        print(content.toString())
    }
    fun printHidden() {
        when (status) {
            CellStatus.UNKNOWN -> print(SAFE_CELL.toString())
            CellStatus.MARKED_MINE -> print(MARKED_MINE_CELL.toString())
            CellStatus.MARKED_FREE -> {
                when (content) {
                    SAFE_CELL -> print(MARKED_FREE_CELL.toString())
                    else -> print(content.toString())
                }
            }
        }
    }
    fun printExploded() {
        when (status) {
            CellStatus.MARKED_FREE -> {
                when (content) {
                    SAFE_CELL -> print(MARKED_FREE_CELL.toString())
                    else -> print(content.toString())
                }
            }
            else -> print(content.toString())
        }
    }
}
class Field(private val _height: Int, private val _width: Int) {
    var fieldStatus = GameStatus.GAME
    private val _field = MutableList(_height) { MutableList(_width) { Cell() } }

    // took this solution from here: https://hyperskill.org/projects/8/stages/47/implement#solutions-309746
    fun generateField(numOfMines: Int) {
        var restMines = numOfMines
        var restCells = _height * _width
        for (r in 0.._field.lastIndex) {
            for (c in 0.._field[r].lastIndex) {
                if (Random.nextInt(0, restCells) < restMines) {
                    placeMine(r, c)
                    restMines--
                }
                restCells--
            }
        }
    }
    fun printOpenField() {
        printColumnCoordinates()
        printHorizontalDelimiters()
        for (r in 0.._field.lastIndex) {
            print("${r + 1}|")
            for (cell in _field[r]) {
                cell.printOpen()
            }
            println("|")
        }
        printHorizontalDelimiters()
    }
    fun printCurrentHiddenField() {
        printColumnCoordinates()
        printHorizontalDelimiters()
        for (r in 0.._field.lastIndex) {
            print("${r + 1}|")
            for (cell in _field[r]) {
                cell.printHidden()
            }
            println("|")
        }
        printHorizontalDelimiters()
    }
    fun printExplodedField() {
        printColumnCoordinates()
        printHorizontalDelimiters()
        for (r in 0.._field.lastIndex) {
            print("${r + 1}|")
            for (cell in _field[r]) {
                cell.printExploded()
            }
            println("|")
        }
        printHorizontalDelimiters()
    }
    fun setOrDeleteMineMark(row: Int, col: Int) = when {
        row !in 0 until _height || col !in 0 until _width -> {
            println("error: coordinates out of bounds")
            false
        }
        else -> {
            _field[row][col].status = when (_field[row][col].status) {
                CellStatus.MARKED_MINE -> CellStatus.UNKNOWN
                CellStatus.UNKNOWN -> CellStatus.MARKED_MINE
                else -> _field[row][col].status
            }
            if (isFieldComplete()) {
                fieldStatus = GameStatus.VICTORY
            }
            true
        }
    }
    fun setFreeMark(row: Int, col: Int) = when {
            row !in 0 until _height || col !in 0 until _width -> {
                println("error: coordinates out of bounds")
                false
            }
            _field[row][col].content == MINE_CELL -> {
                fieldStatus = GameStatus.DEFEAT
                true
            }
            else -> {
                setFreeMarkRecursive(row, col)
                if (isFieldComplete()) {
                    fieldStatus = GameStatus.VICTORY
                }
                true
            }
        }

    private fun isFieldComplete(): Boolean {
        var isAllMinesMarked = true
        var isAllEmptyCellsFree = true
        for (r in 0.._field.lastIndex) {
            for (cell in _field[r]) {
                when (cell.content) {
                    MINE_CELL -> when (cell.status) {
                        CellStatus.MARKED_MINE -> {}
                        else -> isAllMinesMarked = false
                    }
                    else -> when (cell.status) {
                        CellStatus.MARKED_FREE -> {}
                        else -> isAllEmptyCellsFree = false
                    }
                }
                if (!(isAllMinesMarked || isAllEmptyCellsFree)) {
                    return false
                }
            }
        }
        return true
    }
    // took this solution from here: https://hyperskill.org/projects/8/stages/48/implement#solutions-190159
    private fun placeMine(row: Int, col: Int) {
        _field[row][col].content = MINE_CELL
        for (r in maxOf(row - 1, 0)..minOf(row + 1, _height - 1)) {
            for (c in maxOf(col - 1, 0)..minOf(col + 1, _width - 1)) {
                when (_field[r][c].content) {
                    SAFE_CELL -> _field[r][c].content = '1'
                    in '1'..'7' -> _field[r][c].content++
                }
            }
        }
    }
    private fun setFreeMarkRecursive(row: Int, col: Int) {
        when {
            row !in 0 until _height || col !in 0 until _width -> {
                return
            }
            _field[row][col].content in '1' .. '7' -> {
                _field[row][col].status = CellStatus.MARKED_FREE
                return
            }
            else -> {
                _field[row][col].status = CellStatus.MARKED_FREE
                for (r in maxOf(row - 1, 0)..minOf(row + 1, _height - 1)) {
                    for (c in maxOf(col - 1, 0)..minOf(col + 1, _width - 1)) {
                        when {
                            r == row && c == col -> continue
                            _field[r][c].status == CellStatus.MARKED_FREE -> continue
                        }
                        setFreeMarkRecursive(r, c)
                    }
                }
            }
        }
    }
    private fun printColumnCoordinates() {
        print(" |")
        for (i in 1.._width) {
            print(i)
        }
        println("|")
    }
    private fun printHorizontalDelimiters() {
        print("-|")
        for (i in 1.._width) {
            print("-")
        }
        println("|")
    }
}