package minesweeper.launch

import minesweeper.minesweeperCLI.MinesweeperCLI

//TODO: make possibility to set field size
const val FIELD_HEIGHT = 9
const val FIELD_WIDTH = 9
fun main() {
    val minesweeperCLI = MinesweeperCLI(FIELD_HEIGHT, FIELD_WIDTH)
    minesweeperCLI.play()
}