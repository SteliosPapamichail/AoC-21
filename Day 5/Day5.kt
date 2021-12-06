package day5

import java.io.File

fun main() {
    val dataLists: HashMap<Pair<Int, Int>, Int> = hashMapOf()
    File("C:\\Users\\Steli\\Desktop\\day_5.txt")
        .forEachLine { line ->
            val coords: ArrayList<Int> = arrayListOf()
            line.replace(" -> ", ",").split(",")
                .forEach { str -> coords.add(str.trim().toInt()) }

            if (coords[0] == coords[2] || coords[1] == coords[3]) { // horizontal and vertical lines

                if (coords[0] != coords[2]) { // y1 == y2
                    var x: Int
                    val limit: Int
                    if (coords[0] > coords[2]) {
                        x = coords[2] - 1
                        limit = coords[0]
                    } else {
                        x = coords[0] - 1
                        limit = coords[2]
                    }

                    do {
                        ++x
                        if (!dataLists.contains(Pair(x, coords[1]))) dataLists[Pair(x, coords[1])] = 1
                        else dataLists[Pair(x, coords[1])] = dataLists[Pair(x, coords[1])]!!.plus(1)
                    } while (x < limit)
                }

                if (coords[1] != coords[3]) { // x1 == x2
                    var y: Int
                    val limit: Int
                    if (coords[1] > coords[3]) {
                        y = coords[3] - 1
                        limit = coords[1]
                    } else {
                        y = coords[1] - 1
                        limit = coords[3]
                    }

                    do {
                        ++y
                        if (!dataLists.contains(Pair(coords[0], y))) dataLists[Pair(coords[0], y)] = 1
                        else dataLists[Pair(coords[0], y)] = dataLists[Pair(coords[0], y)]!!.plus(1)
                    } while (y < limit)
                }

            } else { // diagonal lines
                var x = coords[0]
                var y = coords[1]
                val xLimit = coords[2]
                val yLimit = coords[3]

                if ( x < xLimit && y < yLimit ) {
                    while(x <= xLimit) {
                        if (!dataLists.contains(Pair(x, y))) dataLists[Pair(x, y)] = 1
                        else dataLists[Pair(x, y)] = dataLists[Pair(x, y)]!!.plus(1)
                        println("$x,$y")
                        x++
                        y++
                    }
                } else if ( x < xLimit && y > yLimit ) {
                    while(x <= xLimit) {
                        if (!dataLists.contains(Pair(x, y))) dataLists[Pair(x, y)] = 1
                        else dataLists[Pair(x, y)] = dataLists[Pair(x, y)]!!.plus(1)
                        println("$x,$y")
                        x++
                        y--
                    }
                } else if ( x > xLimit && y < yLimit ) {
                    while(x >= xLimit) {
                        if (!dataLists.contains(Pair(x, y))) dataLists[Pair(x, y)] = 1
                        else dataLists[Pair(x, y)] = dataLists[Pair(x, y)]!!.plus(1)
                        println("$x,$y")
                        x--
                        y++
                    }
                } else if ( x > xLimit && y > yLimit ) {
                    while(x >= xLimit) {
                        if (!dataLists.contains(Pair(x, y))) dataLists[Pair(x, y)] = 1
                        else dataLists[Pair(x, y)] = dataLists[Pair(x, y)]!!.plus(1)
                        println("$x,$y")
                        x--
                        y--
                    }
                }
            }
        }

    var numOfOverlappingLines = 0
    dataLists.forEach { (_, v) -> if (v >= 2) numOfOverlappingLines++ }
    println(numOfOverlappingLines)
}