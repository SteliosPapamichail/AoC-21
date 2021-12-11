import java.io.File

val energyLevels = Array(10) { IntArray(10) }
val adjacentCells = arrayOf(
    Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(-1, -1), Pair(0, -1), Pair(-1, 0), Pair(-1, 1), Pair(1, -1)
)

fun main() {
    var flashes = 0
    val steps = 100
    var synchronizationStep = -1
    var step = 1
    parseInput(energyLevels)

    while(synchronizationStep == -1) {
        val flashedOctopi = mutableSetOf<Pair<Int,Int>>()
        energyLevels.forEachIndexed { i, ints ->
            ints.forEachIndexed { j, _ ->
                if(step <= steps) flashes += countFlashes(i,j,flashedOctopi)
                else countFlashes(i,j,flashedOctopi)
            }
        }
        if(flashedOctopi.size == 100) synchronizationStep = step
        step++
    }
    println("partA = $flashes | partB = $synchronizationStep")
}

fun countFlashes(i:Int, j:Int, flashedOctopi:MutableSet<Pair<Int,Int>>) : Int {
    if(i < 0 || i > energyLevels.size-1 || j < 0 || j > energyLevels[i].size-1) return 0
    var flashes = 0

    if(!flashedOctopi.contains(Pair(i,j)) && ++energyLevels[i][j] > 9) {
        energyLevels[i][j] = 0
        flashes++
        flashedOctopi.add(Pair(i,j))

        for(pair in adjacentCells) {
            flashes += countFlashes(i+pair.first,j+pair.second, flashedOctopi)
        }
    }

    return flashes
}

private fun parseInput(energyLevels: Array<IntArray>) {
    var k = 0
    var j = 0
    File("C:/Users/Steli/Desktop/day11.txt").forEachLine { line ->
        line.forEach { c ->
            energyLevels[k][j] = c.toString().toInt()
            j++
        }
        j = 0
        k++
    }
}