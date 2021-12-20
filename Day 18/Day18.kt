import com.github.shiguruikai.combinatoricskt.permutations
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {
    var magnitude = 0L
    var maxMagnitudeInPerm = 0L
    measureTimeMillis {
        val snailfishNumbers = File("C:/Users/Steli/Desktop/day18.txt").readLines()
        var snailfishNum = snailfishNumbers[0]
        snailfishNumbers.forEachIndexed { index, line ->
            if (index == 0) return@forEachIndexed
            // perform addition first
            snailfishNum = "[$snailfishNum,$line]"
            snailfishNum = performReduction(snailfishNum)
        }
        magnitude = calculateMagnitude(snailfishNum)
        maxMagnitudeInPerm = findMaxMagnitudeInPermutations(snailfishNumbers)
    }.also { time ->
        println("Magnitude = $magnitude in $time ms") // part A
        println("Max magnitude in permutations = $maxMagnitudeInPerm in $time ms") // part B
    }
}

fun extractPairNums(pair: String): Pair<Int, Int> {
    val numbers = pair.removePrefix("[").removeSuffix("]").split(',')
    return numbers[0].toInt() to numbers[1].toInt()
}

fun pairLevel(endIndex: Int, snailfishNum: String): Int {
    val leftPart = snailfishNum.substring(0, endIndex)
    return leftPart.count { it == '[' } - leftPart.count { it == ']' }
}

/**
 * If any pair is nested inside four pairs, the leftmost such pair explodes.
 */
fun explodeLeft(leftPairNum: Int, leftPart: String): String {
    val singleLeftNumRegex = Regex("(\\d+)")
    val matches = singleLeftNumRegex.findAll(leftPart)
    if (matches.none()) return leftPart
    val leftNum = matches.last()
    return leftPart.substring(0, leftNum.range.first) + (leftPairNum + leftNum.value.toInt()) +
            leftPart.substring(leftNum.range.last + 1, leftPart.length)
}

fun explodeRight(rightPairNum: Int, rightPart: String): String {
    val singleRightNumRegex = Regex("(\\d+)")
    val matches = singleRightNumRegex.findAll(rightPart)
    if (matches.none()) return rightPart
    val rightNum = matches.first()
    return rightPart.substring(0, rightNum.range.first) + (rightPairNum + rightNum.value.toInt()) +
            rightPart.substring(rightNum.range.last + 1, rightPart.length)
}

fun explode(snailfishNum: String): Pair<String, Boolean> {
    val pairRegex = Regex("(\\[\\d+,\\d+])")
    val match = pairRegex.findAll(snailfishNum).firstOrNull {
        pairLevel(it.range.first, snailfishNum) == 4
    } ?: return snailfishNum to false
    val (pairNumLeft, pairNumRight) = extractPairNums(match.value)
    var leftPart = snailfishNum.substring(0, match.range.first)
    var rightPart = snailfishNum.substring(match.range.last + 1, snailfishNum.length)
    leftPart = explodeLeft(pairNumLeft, leftPart)
    rightPart = explodeRight(pairNumRight, rightPart)
    return (leftPart + "0" + rightPart) to true
}


fun split(snailfishNum: String): Pair<String, Boolean> {
    val numRegex = Regex("(\\d+)")
    val match = numRegex.findAll(snailfishNum).firstOrNull { it.value.toInt() > 9 } ?: return snailfishNum to false

    val leftNum = floor((match.value.toDouble() / 2))
    val rightNum = ceil((match.value.toDouble() / 2))
    val leftPart = snailfishNum.substring(0, match.range.first)
    val rightPart = snailfishNum.substring(match.range.last + 1, snailfishNum.length)
    return ("$leftPart[${leftNum.toInt()},${rightNum.toInt()}]$rightPart") to true
}

fun performReduction(snailfishNum: String): String {
    /**
    If any regular number is 10 or greater, the leftmost such regular number splits.
     */
    var result = snailfishNum
    while (true) {
        val explosionData = explode(result)
        result = explosionData.first
        if (explosionData.second) continue // one operation each time
        val splitData = split(result)
        result = splitData.first
        if (!splitData.second && !explosionData.second) return result // no more reduction possible
    }
}

fun calculateMagnitude(snailfishNum: String): Long {
    val pairRegex = Regex("(\\[\\d+,\\d+])")
    val sfNumList = pairRegex.findAll(snailfishNum)

    if (sfNumList.none()) return snailfishNum.toLong() // one number is left

    sfNumList.forEach { match ->
        val (leftNum, rightNum) = extractPairNums(match.value)
        val result = leftNum * 3 + rightNum * 2
        val newSnailfishNum = snailfishNum.substring(
            0,
            match.range.first
        ) + result.toString() + snailfishNum.substring(match.range.last + 1, snailfishNum.length)
        return calculateMagnitude(newSnailfishNum)
    }

    error("Something went wrong calculating snailfish number magnitude!")
}

/**
 * Using the https://github.com/shiguruikai/combinatoricskt library
 */
fun findMaxMagnitudeInPermutations(snailfishNumbers: List<String>): Long {
    var maxMagnitude = 0L
    snailfishNumbers.permutations(2).forEach { perm ->
        val reducedNum = performReduction("[${perm[0]},${perm[1]}]")
        val magnitude = calculateMagnitude(reducedNum)
        maxMagnitude = max(maxMagnitude, magnitude)
    }
    return maxMagnitude
}
