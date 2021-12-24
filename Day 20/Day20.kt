import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val lines = File("C:/Users/Steli/Desktop/Day20.txt").readLines().filter { it.isNotEmpty() }
        val enhancementAlgorithmLine = lines[0]
        val initImage = parseImage(lines.subList(1, lines.size))
        enhanceImage(initImage, 2, enhancementAlgorithmLine)
        enhanceImage(initImage, 50, enhancementAlgorithmLine)
    }.also {
        println("Both parts ran in $it ms")
    }
}

fun enhanceImage(image: Array<CharArray>, iterations: Int, enhancementAlg: String) {
    var flipPixel = '.'
    var litOutputPixels = 0
    var inputImg = generateNewInputImage(image, flipPixel)
    var outputImage = Array(image.size + 2) { CharArray(image[0].size + 2) }

    repeat(iterations) {
        litOutputPixels = 0
        inputImg.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, _ -> // for each input pixel
                var pixelStr = ""
                // neighboring pixels
                arrayOf(
                    rowIndex - 1 to colIndex - 1, rowIndex - 1 to colIndex, rowIndex - 1 to colIndex + 1,
                    rowIndex to colIndex - 1, rowIndex to colIndex, rowIndex to colIndex + 1,
                    rowIndex + 1 to colIndex - 1, rowIndex + 1 to colIndex, rowIndex + 1 to colIndex + 1
                ).forEach { pair ->
                    pixelStr += if (pair.first in inputImg.indices && pair.second in inputImg[0].indices) {
                        if (inputImg[pair.first][pair.second] == '.') "0" else "1"
                    } else {
                        if (flipPixel == '.') "0" else "1"
                    }
                }
                val outputPixel = enhancementAlg[pixelStr.toInt(2)]
                if (outputPixel == '#') litOutputPixels++
                outputImage[rowIndex][colIndex] = outputPixel
            }
        }
        flipPixel = if (flipPixel == '.') '#' else '.'
        inputImg = generateNewInputImage(outputImage, flipPixel)
        outputImage = Array(inputImg.size) { CharArray(inputImg[0].size) }
    }
    //printImage(inputImg)
    println(litOutputPixels)
}

fun printImage(image: Array<CharArray>) {
    image.forEach { line ->
        line.forEach {
            print("$it ")
        }
        println()
    }
}

private fun parseImage(lines: List<String>): Array<CharArray> {
    val inputImg = Array(lines.size) { CharArray(lines[0].length) }
    for (i in lines.indices) {
        for (j in lines[0].indices) {
            inputImg[i][j] = lines[i][j]
        }
    }
    return inputImg
}

private fun generateNewInputImage(inputImg: Array<CharArray>, pixel: Char): Array<CharArray> {
    val parsedInputImg = Array(inputImg.size + 2) { CharArray(inputImg[0].size + 2) { pixel } }
    for (row in 1..inputImg.size) {
        for (col in 1..inputImg[0].size) {
            parsedInputImg[row][col] = inputImg[row - 1][col - 1]
        }
    }
    return parsedInputImg
}