import java.io.File
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    val riskMap = Array(100) { IntArray(100) }
    var i = 0
    File("C:/Users/Steli/Desktop/day15.txt").forEachLine { line ->
        riskMap[i] = line.map { it.toString().toInt() }.toIntArray()
        i++
    }

    println(bellmanFord(riskMap))
    val expandedRiskMap = generateExpandedMap(riskMap)
    println(bellmanFord(expandedRiskMap))

}

fun generateExpandedMap(riskMap: Array<IntArray>): Array<IntArray> {
    val expandedMap = Array(5 * riskMap.size) { IntArray(5 * riskMap[0].size) }
    riskMap.forEachIndexed { row, ints ->
        ints.forEachIndexed { col, risk ->
            // the risk will be repeated 25 times, increasing each time
            // and wrapping-around if needed
            for (i in 0 until 5) {
                for (j in 0 until 5) {
                    var newRisk = risk + i + j
                    if (newRisk > 9) newRisk -= 9
                    // use offsets for the new position in the expanded map
                    expandedMap[row + (i * riskMap.size)][col + (j * riskMap[0].size)] = newRisk
                }
            }
        }
    }
    return expandedMap
}

fun bellmanFord(riskMap: Array<IntArray>): Int {
    val riskSums = Array(riskMap.size) { IntArray(riskMap[0].size) }

    for (row in riskSums.indices) {
        for (col in riskSums[0].indices) {
            riskSums[row][col] = 10000
        }
    }
    riskSums[riskSums.size - 1][riskSums[0].size - 1] = 0

    var riskCanBeReduced = true
    while (riskCanBeReduced) {
        riskCanBeReduced = false
        for (row in riskSums.size - 1 downTo 0) {
            for (col in riskSums[0].size - 1 downTo 0) {
                var minRisk = Int.MAX_VALUE

                // check risk of adjacent cells
                if (row - 1 >= 0) minRisk = min(minRisk, riskMap[row - 1][col] + riskSums[row - 1][col])
                if (row + 1 < riskSums.size) minRisk = min(minRisk, riskMap[row + 1][col] + riskSums[row + 1][col])
                if (col - 1 >= 0) minRisk = min(minRisk, riskMap[row][col - 1] + riskSums[row][col - 1])
                if (col + 1 < riskSums[0].size) minRisk = min(minRisk, riskMap[row][col + 1] + riskSums[row][col + 1])

                val oldRiskSum = riskSums[row][col]
                riskSums[row][col] = min(minRisk, riskSums[row][col])
                if (oldRiskSum != riskSums[row][col]) riskCanBeReduced = true
            }
        }
    }
    return riskSums[0][0]
}
