package minesweeper.field

import kotlin.random.Random

const val CLOSED_CELL = '.'
const val MINE_CELL = 'X'
const val MARKED_CELL = '*'

data class Cell(var content: Char = CLOSED_CELL, var isMarked: Boolean = false)

class Field(private val _height: Int, private val _width: Int) {
    private val _field = MutableList(_height) { MutableList(_width) { Cell() } }

    // took this solution from here: https://hyperskill.org/projects/8/stages/47/implement#solutions-309746
    fun generateField(numOfMines: Int) {
        var restMines = numOfMines
        var restCells = _height * _width
        for (i in 0.._field.lastIndex) {
            for (j in 0.._field[i].lastIndex) {
                if (Random.nextInt(0, restCells) < restMines) {
                    placeMine(i, j)
                    restMines--
                }
                restCells--
            }
        }
    }

    fun printOpenField() {
        printColumnCoordinates()
        printHorizontalDelimiters()
        for (i in 0.._field.lastIndex) {
            print("$i|")
            for (cell in _field[i]) {
                print(cell.content.toString())
            }
            println("|")
        }
        printHorizontalDelimiters()
    }

    fun printCloseField() {
        printColumnCoordinates()
        printHorizontalDelimiters()
        for (i in 0.._field.lastIndex) {
            print("${i + 1}|")
            for (cell in _field[i]) {
                if (cell.content in '1'..'7') {
                    print(cell.content.toString())
                } else {
                    if (cell.isMarked) {
                        print(MARKED_CELL.toString())
                    } else {
                        print(CLOSED_CELL.toString())
                    }
                }
            }
            println("|")
        }
        printHorizontalDelimiters()
    }
    fun setOrDeleteMark(row: Int, col: Int) = if (_field[row][col].content in '1'..'7') { //todo: validate row and col
        println("There is a number here!")
        false
    } else {
        _field[row][col].isMarked = true
        true
    }

    // took this solution from here: https://hyperskill.org/projects/8/stages/48/implement#solutions-190159
    private fun placeMine(row: Int, col: Int) {
        _field[row][col].content = MINE_CELL
        for (r in maxOf(row - 1, 0)..minOf(row + 1, _height - 1)) {
            for (c in maxOf(col - 1, 0)..minOf(col + 1, _width - 1)) {
                when (_field[r][c].content) {
                    CLOSED_CELL -> _field[r][c].content = '1'
                    in '1'..'7' -> _field[r][c].content++
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