package day6

import java.io.File

fun main() {
    val fishes = LongArray(9)
    for(i in 0 until 9) fishes[i] = 0

    val days = 256
    File("C:/Users/Steli/Desktop/day_6.txt").forEachLine { line ->
        line.split(',').forEach {
            val num = it.toInt()
            fishes[num] += 1L
        }
    }

    for (day in 1..days) {

        val fishesToGenerate = fishes[0]
        fishes[0] = 0
        for(i in 1 until 9) {
            val fishesToMove = fishes[i]
            fishes[i] = 0
            fishes[i-1] += fishesToMove
        }
        fishes[8] += fishesToGenerate
        fishes[6] += fishesToGenerate
    }
    var numOfFish = 0L
    fishes.forEach { numOfFish += it }
    println(numOfFish)
}