import java.io.File
import java.util.*


fun main() {
    val mappings = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    solvePartA(mappings)
    solvePartB(mappings)
}

private fun solvePartA(
    mappings: Map<Char, Char>
) {
    val lineStack = Stack<Char>()
    val invalidChars = arrayListOf<Char>()
    val syntaxErrorScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    File("C:/Users/Steli/Desktop/day10.txt").forEachLine outer@{ line ->
        line.forEach { c: Char ->
            if (mappings.containsKey(c)) lineStack.push(c)
            else {
                if (c != mappings[lineStack.peek()]) {
                    invalidChars.add(c)
                    return@outer
                } else lineStack.pop()
            }
        }
    }

    val score = invalidChars.sumOf { ch ->
        syntaxErrorScores[ch]!!.toLong()
    }
    println(score)
}

private fun solvePartB(
    mappings: Map<Char, Char>
) {
    val lineStack = Stack<Char>()
    val lineScores = arrayListOf<Long>()
    val pointsTable = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )
    File("C:/Users/Steli/Desktop/day10.txt").forEachLine outer@{ line ->
        line.forEach { c: Char ->
            if (mappings.containsKey(c)) lineStack.push(c)
            else {
                if (c != mappings[lineStack.peek()]) {
                    lineStack.clear()
                    return@outer
                } else lineStack.pop()
            }
        }
        if (lineStack.isEmpty()) return@outer
        var lineScore = 0L
        lineStack.reverse()
        lineStack.forEach { ch ->
            //print(mappings[ch])
            lineScore = lineScore * 5 + pointsTable[mappings[ch]]!!
        }
        lineStack.clear()
        lineScores.add(lineScore)
        //println()
    }
    lineScores.sort()
    println(lineScores[lineScores.size / 2])
}
