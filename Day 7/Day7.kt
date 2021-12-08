import java.io.File
import kotlin.math.abs

fun main() {
    val data = arrayListOf<Int>()
    File("C:/Users/Steli/Desktop/day_7.txt").forEachLine { line ->
        line.split(",").forEach {
            data.add(it.toInt())
        }
    }

    // part a
    var median: Int
    data.sorted().let {
        median = if(it.size%2 == 0) (it[it.size/2] + it[(it.size-1)/2]) / 2 else it[it.size/2]
    }

    // part b
    var sum = 0
    data.forEach { sum += it }
    val mean = sum/data.size

    // fuel expenditure calculations
    var fuelSpentA = 0
    var fuelSpentB = 0

    data.forEach { x ->
        fuelSpentA += abs(x-median)
        val distance = abs(x-mean)
        fuelSpentB += distance*(distance+1)/2 // 1+2+3+...+n = n(n+1)/2
    }

    println("fuelA = $fuelSpentA | fuelB = $fuelSpentB")
}