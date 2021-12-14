import java.io.File
import kotlin.system.measureTimeMillis

val bindings = mutableMapOf<String, Char>()
var template = ""

fun main() {
    File("C:/Users/Steli/Desktop/day14.txt").forEachLine { line ->
        if (line.isEmpty()) return@forEachLine
        if (!line.contains(" -> ")) {
            template = line
            return@forEachLine
        }
        val binding = line.split(" -> ")
        bindings[binding[0]] = binding[1][0]
    }

    val pairCounts = hashMapOf<String, Long>()
    for (i in 0 until template.length - 1) {
        pairCounts.merge("${template[i]}${template[i + 1]}", 1L) { l1, l2 -> l1 + l2 }
    }

    solveForNumOfSteps(10, pairCounts)
    solveForNumOfSteps(40, pairCounts)
}

private fun solveForNumOfSteps(
    steps: Int,
    pairCounts: HashMap<String, Long>
) {
    var pairCounts1 = pairCounts
    for (i in 0 until steps) {
        val tmp = hashMapOf<String, Long>()
        pairCounts1.keys.forEach { key ->
            tmp.merge("${key[0]}${bindings[key]}", pairCounts1[key]!!) { l1, l2 -> l1 + l2 }
            tmp.merge("${bindings[key]}${key[1]}", pairCounts1[key]!!) { l1, l2 -> l1 + l2 }
        }
        pairCounts1 = tmp
    }

    val charCounts = mutableMapOf<Char, Long>()
    pairCounts1.forEach { (k, v) ->
        charCounts.merge(k[0], v) { l1, l2 -> l1 + l2 }
    }
    // include last char
    charCounts.merge(template[template.length - 1], 1L) { l1, l2 -> l1 + l2 }

    val max = charCounts.maxByOrNull { (_, v) -> v }!!.value
    val min = charCounts.minByOrNull { (_, v) -> v }!!.value
    println("max = $max | min = $min | ${max - min}")
}