import java.io.File
import java.math.BigInteger

fun main() {
    val input = File("C:/Users/Steli/Desktop/day16.txt").readLines()
    val binary = input[0].hexToBinStr()
    printColorCodingScheme()
    calculateVersionSum(binary) // part A
    evaluateBITSTransmission(binary) // part B
}

private fun printColorCodingScheme() {
    colored {
        println("Color coding: ".bold + "Version".red.bold + " | " + "TypeId".yellow.bold + " | " + "Operator Mode".purple.bold + " | " + "Operator".blue.bold + " | " + "Literal".green.bold + "\n--------------------------------------------------------------------".bold)
    }
}

fun calculateVersionSum(binary: String) {
    val (packet, _) = parseInput(binary)
    println("\nVersion sum = " + packet.versionSum())
}

fun evaluateBITSTransmission(binary: String) {
    val (packet, _) = parseInput(binary)
    println("\nResult evaluation = " + packet.evaluateOperation())
}

sealed class Packet(
    val version: Int,
    val typeId: Int
) {
    open fun versionSum() = version
    abstract fun evaluateOperation(): Long
}

class LiteralPacket(
    version: Int,
    private val number: Long
) : Packet(version, 4) {
    override fun evaluateOperation(): Long = number
}

class OperatorPacket(
    version: Int,
    typeId: Int,
    private val subPackets: List<Packet>
) : Packet(version, typeId) {
    override fun versionSum(): Int {
        return version + subPackets.sumOf { it.versionSum() }
    }

    override fun evaluateOperation(): Long {
        return when (typeId) {
            0 -> subPackets.sumOf { it.evaluateOperation() }
            1 -> subPackets.fold(1L) { acc, packet -> acc * packet.evaluateOperation() }
            2 -> subPackets.minOfOrNull { it.evaluateOperation() } ?: 0L
            3 -> subPackets.maxOfOrNull { it.evaluateOperation() } ?: 0L
            5 -> if (subPackets[0].evaluateOperation() > subPackets[1].evaluateOperation()) 1L else 0L
            6 -> if (subPackets[0].evaluateOperation() < subPackets[1].evaluateOperation()) 1L else 0L
            7 -> if (subPackets[0].evaluateOperation() == subPackets[1].evaluateOperation()) 1L else 0L
            else -> error("Invalid type ID $typeId")
        }
    }
}

data class ParsedGenericPacket(val packet: Packet, val offset: Int)
data class ParsedLiteralPacket(val num: Long, val offset: Int)
data class ParsedOperatorPacket(val subPackets: List<Packet>, val offset: Int)

fun parseInput(binary: String): ParsedGenericPacket {
    val versionBin = binary.substring(0, 3)
    val version = versionBin.binToInt()
    val typeIDBin = binary.substring(3, 6)
    val typeID = typeIDBin.binToInt()
    colored {
        print(versionBin.red.bold + typeIDBin.yellow.bold)
    }
    return if (typeID == 4) {
        val (num, offset) = parseLiteralPacketNum(binary.substring(6))
        ParsedGenericPacket(LiteralPacket(version, num), 6 + offset)
    } else { // operator
        val (subPackets, offset) = parseOperatorPacket(binary.substring(6))
        ParsedGenericPacket(OperatorPacket(version, typeID, subPackets), 6 + offset)
    }
}

fun parseOperatorPacket(binary: String): ParsedOperatorPacket {
    var offset = 1
    val subPackets = mutableListOf<Packet>()
    colored { print("${binary[0]}".purple.bold) } // mode bit

    when (binary[0]) {
        '0' -> {
            val subPacketLenBin = binary.substring(offset, offset + 15)
            colored { print(subPacketLenBin.cyan.bold) }
            val totalSubPacketLength = subPacketLenBin.binToInt()
            offset += 15 + totalSubPacketLength
            var subPacketsOffset = 0

            while (subPacketsOffset < totalSubPacketLength) {
                val (subPacket, parsedLen) = parseInput(binary.substring(16 + subPacketsOffset))
                subPackets.add(subPacket)
                subPacketsOffset += parsedLen
            }
        }
        '1' -> {
            val numOfSubPacketsBin = binary.substring(offset, offset + 11)
            colored { print(numOfSubPacketsBin.blue.bold) }
            val numOfSubPackets = numOfSubPacketsBin.binToLong()
            offset += 11
            var subPacketsOffset = 0

            for (i in 0 until numOfSubPackets) {
                val (subPacket, parsedLen) = parseInput(binary.substring(12 + subPacketsOffset))
                subPackets.add(subPacket)
                subPacketsOffset += parsedLen
            }

            offset += subPacketsOffset
        }
    }
    return ParsedOperatorPacket(subPackets, offset)
}

fun parseLiteralPacketNum(binary: String): ParsedLiteralPacket {
    var offset = 0
    var number = ""

    while (offset + 5 <= binary.length) {
        val group = binary.substring(offset, offset + 5)
        colored { print(group.green.bold) }
        number += group.substring(1) // skip first group bit
        offset += 5
        if (group[0] == '0') break
    }
    return ParsedLiteralPacket(number.binToLong(), offset)
}

fun String.binToInt() =
    this.fold(0) { acc, ch ->
        when (ch) {
            '1' -> acc * 2 + 1
            else -> acc * 2
        }
    }

fun String.binToLong() =
    this.fold(0L) { acc, ch ->
        when (ch) {
            '1' -> acc * 2L + 1L
            else -> acc * 2L
        }
    }

fun String.hexToBinStr() = this
    .map { hexCharToBin(it) }
    .joinToString(separator = "")

fun hexCharToBin(hex: Char): String {
    var ret = BigInteger("$hex", 16).toString(2)
    while (ret.length < 4) {
        ret = "0$ret"
    }
    return ret
}