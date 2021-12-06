import java.io.File

fun main(args: Array<String>) {
    var isFirstLine = true
    val numbers = arrayListOf<Int>()
    val boards = arrayListOf<Board>()
    var board:Board? = null
    var i=0
    File("C:\\Users\\Steli\\Desktop\\day4.txt")
        .forEachLine { line ->
           if(isFirstLine) { // get the numbers list
               line.split(",").forEach { numStr -> numbers.add(
                   numStr.toInt()
               ) }
               isFirstLine = false
           } else { // create the boards
               if(line.isBlank() || line.isEmpty()) {
                   if(board != null) boards.add(board!!)
                   board = Board()
                   i = 0
               } else { // add line to board
                   val numStrs = line.split(" ").filter {
                       it.trim().isNotBlank()
                   }
                   for(j in numStrs.indices) {
                       val numStr = numStrs[j].trim()
                       if(numStr.isBlank()) continue
                       board!!.matrix[i][j] = numStrs[j].trim().toInt()
                   }
                   i++
               }
           }
        }
    boards.add(board!!) // add final board

    val winningBoards = arrayListOf<Board>()
    numbers.forEach { num ->
        boards.forEach { b ->
            b.markIfExists(num)
            if(b.boardWins() && b.winningScore == 0) {
                b.calculateWinningScore(num)
                winningBoards.add(b)
            }
        }
    }

    println(winningBoards.size)
    println("First winning board's score is ${winningBoards[0].winningScore}\nLast winning board's score is ${winningBoards[winningBoards.size-1].winningScore}")
}