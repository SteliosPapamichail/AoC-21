import java.io.File
import kotlin.math.abs

val dots = mutableSetOf<Pair<Int, Int>>()
val folds = arrayListOf<Pair<Char, Int>>()
val dotPointsToPreserve = mutableSetOf<Pair<Int, Int>>()

fun main() {
    File("C:/Users/Steli/Desktop/day13.txt").forEachLine { line ->
        if (line.isEmpty()) return@forEachLine
        if (line.contains(",")) {
            val point = line.split(",")
            dots.add(point[0].toInt() to point[1].toInt())
        } else { // fold instructions
            val data = line.split("=")
            val instructionLength = data[0].length
            folds.add(data[0][instructionLength - 1] to data[1].toInt())
        }
    }

    // part a
    performFold(true)
    println("partA = ${dots.size}")
    // part b
    performFold(false)

    // part b
    println("partB:")
    decodeActivationCode()
}

private fun decodeActivationCode() {
    val maxX = dots.maxByOrNull { it.first }!!.first + 1
    val maxY = dots.maxByOrNull { it.second }!!.second + 1
    val activationCode = Array(maxY) { CharArray(maxX) { '.' } }
    dots.forEach { (x, y) ->
        activationCode[y][x] = '#'
    }
    printActivationCode(activationCode)
}

private fun printActivationCode(activationCode: Array<CharArray>) {
    activationCode.forEach { chars ->
        chars.forEach { c ->
            print(" $c")
        }
        println()
    }
}

private fun performFold(isPartA: Boolean) {
    folds.forEachIndexed { index, pair ->
        if (!isPartA && index == 0) return@forEachIndexed // skip first fold
        dotPointsToPreserve.clear()
        dots.forEach dot@{ (x, y) ->
            when (pair.first) {
                'x' -> {
                    if (x < pair.second) {
                        dotPointsToPreserve.add(x to y)
                        return@dot
                    }
                    val newX = pair.second - abs(pair.second - x)
                    dotPointsToPreserve.add(newX to y)
                }
                'y' -> {
                    if (y < pair.second) {
                        dotPointsToPreserve.add(x to y)
                        return@dot
                    }
                    val newY = pair.second - abs(pair.second - y)
                    dotPointsToPreserve.add(x to newY)
                }
            }
        }
        dots.clear()
        dots.addAll(dotPointsToPreserve)
        if (isPartA) return
    }
}