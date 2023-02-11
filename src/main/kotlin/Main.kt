package minesweeper

fun main() {
    val fieldSize = 10
    val field = MutableList(fieldSize) { MutableList(fieldSize) { '.' } }
    for (i in 0..field.lastIndex) {
        field[i][i] = 'x'
    }

    for (i in 0..field.lastIndex) {
        for (j in field[i]) {
            print(j.toString())
        }
        println()
    }
}
