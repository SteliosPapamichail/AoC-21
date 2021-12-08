import java.io.File

fun main() {
    val dataEntries = ArrayList<Pair<List<Set<Char>>,List<Set<Char>>>>()
    File("C:/Users/Steli/Desktop/day8.txt").forEachLine { line ->
        val data = line.split(" | ")
        val pattern = data[0].split(" ").map { it.toSet() }
        val output = data[1].split(" ").map { it.toSet() }
        dataEntries.add(pattern to output)
    }

    fun solvePartA(): Int {
        return dataEntries.flatMap { it.second }.count { arrayOf(2,3,4,7).contains(it.size) }
    }

    fun solvePartB(): Int {
        return dataEntries.sumOf { (patterns, outputs) ->
            val mappings = mutableMapOf(
                1 to patterns.first { it.size == 2 },
                4 to patterns.first { it.size == 4 },
                7 to patterns.first { it.size == 3 },
                8 to patterns.first { it.size == 7 },
            )

            with(mappings) {
                put(3, patterns.filter { it.size == 5 }.first { it.intersect(getValue(1)).size == 2 })
                put(2, patterns.filter { it.size == 5 }.first { it.intersect(getValue(4)).size == 2 })
                put(5, patterns.filter { it.size == 5 }.first { it !in values })
                put(6, patterns.filter { it.size == 6 }.first { it.intersect(getValue(1)).size == 1 })
                put(9, patterns.filter { it.size == 6 }.first { it.intersect(getValue(4)).size == 4 })
                put(0, patterns.filter { it.size == 6 }.first { it !in values })
            }

            outputs.joinToString("") { output ->
                mappings.filterValues { it == output }.keys.elementAt(0).toString()
            }.toInt()
        }
    }

    println(solvePartA())
    println(solvePartB())
}