class Board {
    val matrix: Array<IntArray> = Array(5) { IntArray(5) }
    private var marked: Array<IntArray> = Array(5) { IntArray(5) }
    var winningScore = 0

    private fun isHorizontalBingo(): Boolean {
        for (i in matrix.indices) {
            val row = matrix[i]
            if(row.contentEquals(marked[i])) return true
        }
        return false
    }

    private fun isVerticalBingo(): Boolean {
        var colIndex = 0
        var i: Int
        while(colIndex < matrix.size-1) {
            i = 0
            while (i < matrix.size - 1) {
                if (matrix[i][colIndex] != marked[i][colIndex]) {
                    break
                }
                i++
                if (i == matrix.size) return true
            }
            colIndex++
        }
        return false
    }

    fun boardWins() : Boolean = isHorizontalBingo() || isVerticalBingo()

    fun markIfExists(num:Int) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val element = matrix[i][j]
                if(element == num) {
                    marked[i][j] = element
                    return
                }
            }
        }
    }

    fun calculateWinningScore(winningNum:Int) {
        var unmarkedScore = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val element = matrix[i][j]
                if(element != marked[i][j]) unmarkedScore += element
            }
        }
        winningScore = unmarkedScore*winningNum
    }

    fun printMatrix() {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                print("${matrix[i][j]} ")
            }
            println()
        }
        println("\n")
    }

    fun printMarked() {
        for (i in marked.indices) {
            for (j in marked[i].indices) {
                print("${marked[i][j]} ")
            }
            println()
        }
        println("\n")
    }
}