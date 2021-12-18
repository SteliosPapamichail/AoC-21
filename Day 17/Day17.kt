import java.awt.Point
import java.io.File
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

fun main() {
    var maxY: Int
    var velCnt: Int
    measureTimeMillis {
        val input = File("C:/Users/Steli/Desktop/day17.txt").readLines()
        val xRangeStr = input[0].split(",")[0].split("=")[1]
        val yRangeStr = input[0].split(",")[1].split("=")[1]
        val xBounds = xRangeStr.split("..")
        val yBounds = yRangeStr.split("..")
        val xRange = xBounds[0].toInt().rangeTo(xBounds[1].toInt())
        val yRange = yBounds[0].toInt().rangeTo(yBounds[1].toInt())

        maxY = findMaxY(yRange) // part A
        velCnt = countDistinctValidVelocities(xRange, yRange) // part B
    }.also { time ->
        println("Part A : $maxY in $time ms")
        println("Part B : $velCnt in $time ms")
    }
}

private fun countDistinctValidVelocities(xRange: IntRange, yRange: IntRange) : Int {
    var velocitiesCnt = 0
    for (xV in 1..xRange.last) {
        for (yV in yRange.first until yRange.first.absoluteValue) {
            velocitiesCnt += countDistinctVelocitiesForTrajectory(xV, yV, xRange, yRange)
        }
    }
    return velocitiesCnt
}

private fun countDistinctVelocitiesForTrajectory(
    vX: Int,
    vY: Int,
    xRange: IntRange,
    yRange: IntRange
) : Int {
    val pos = Point(0, 0)
    var xV = vX
    var yV = vY
    var cnt = 0
    // trajectory steps
    while (pos.x <= xRange.last && pos.y >= yRange.first) {
        if (isInTargetArea(pos, xRange, yRange)) {
            cnt++
            break
        }
        pos.x += xV
        pos.y += yV--
        if (xV > 0) --xV else if (xV < 0) ++xV
    }
    return cnt
}

private fun findMaxY(yRange: IntRange) :Int {
    var yV = -yRange.first - 1
    var y = 0

    while (yV > 0) {
        y += yV--
    }
    return y
}

private fun isInTargetArea(pos: Point, xRange: IntRange, yRange: IntRange): Boolean {
    return pos.x in xRange && pos.y in yRange
}