import java.io.File

fun main() {
    val heightMap = ArrayList<MutableList<Int>>()
    File("C:/Users/Steli/Desktop/day9.txt").forEachLine { line ->
        heightMap.add(line.map { it.toString().toInt() } as MutableList<Int>)
    }

    calculateRiskLevels(heightMap)
    calculateBasins(heightMap)
}

private fun calculateRiskLevels(heightMap: ArrayList<MutableList<Int>>) {
    val lowPoints = arrayListOf<Int>()
    heightMap.forEachIndexed outer@{ index, inner ->
        inner.forEachIndexed inner@{ hIndex, height ->
            if (index - 1 >= 0) { // has list above
                if (heightMap[index - 1][hIndex] <= height) return@inner
            }
            if (index + 1 <= heightMap.size - 1) { // has list below
                if (heightMap[index + 1][hIndex] <= height) return@inner
            }
            if (hIndex - 1 >= 0) { // has element on the left
                if (inner[hIndex - 1] <= height) return@inner
            }
            if (hIndex + 1 <= inner.size - 1) { // has element on the right
                if (inner[hIndex + 1] <= height) return@inner
            }

            // height is the lowest point at this position
            lowPoints.add(height)
        }
    }

    println(lowPoints.sumOf { it + 1 })
}

private fun calculateBasins(heightMap: ArrayList<MutableList<Int>>) {
    val basinSizes = arrayListOf<Int>()
    heightMap.forEachIndexed { index, list ->
        list.forEachIndexed inner@{ hIndex, height ->
            if (height == 9) return@inner
            basinSizes.add(findBasinSize(index, hIndex, heightMap))
        }
    }
    basinSizes.sort()
    basinSizes.reverse()
    println(basinSizes[0] * basinSizes[1] * basinSizes[2])
}

private fun findBasinSize(i: Int, j: Int, heightMap: ArrayList<MutableList<Int>>): Int {
    if (i < 0 || i > heightMap.size - 1 ||
        j < 0 || j > heightMap[i].size - 1 || heightMap[i][j] == 9
    ) return 0
    heightMap[i][j] = 9
    return 1 + findBasinSize(i - 1, j, heightMap) + findBasinSize(i + 1, j, heightMap) + findBasinSize(i, j - 1, heightMap)+ findBasinSize(i, j + 1, heightMap)
}