import java.lang.RuntimeException
import java.lang.StringBuilder
import java.math.BigInteger

abstract class Packet(val version: Int) {
    abstract fun print(indent: Int = 0)

    abstract fun flatten(): Sequence<Packet>

    abstract fun calculate(): Long
}

class Literal(version: Int, val value: Long) : Packet(version) {
    override fun print(indent: Int) {
        println(" ".repeat(indent) + "Literal[v$version]=$value")
    }

    override fun flatten() = sequenceOf(this)

    override fun calculate() = value
}

class Operator(version: Int, val type: Int, val subPackets: List<Packet>) : Packet(version) {
    override fun print(indent: Int) {
        println(" ".repeat(indent) + "Operator[v$version][t$type]")
        subPackets.forEach { it.print(indent + 2) }
    }

    override fun flatten() = sequenceOf(this) + subPackets.asSequence().flatMap { it.flatten() }

    override fun calculate() = when(type) {
        0 -> subPackets.sumOf { it.calculate() }
        1 -> subPackets.fold(1L) {acc, packet -> acc*packet.calculate() }
        2 -> subPackets.minOf { it.calculate() }
        3 -> subPackets.maxOf { it.calculate() }
        5 -> if(subPackets[0].calculate() > subPackets[1].calculate()) 1 else 0
        6 -> if(subPackets[0].calculate() < subPackets[1].calculate()) 1 else 0
        7 -> if(subPackets[0].calculate() == subPackets[1].calculate()) 1 else 0
        else -> throw RuntimeException()
    }

}

class PacketDeserializer(input: String) {

    private val iterator = input.asSequence().flatMap { toBinary(it).asSequence().map(Char::digitToInt) }.iterator()
    private var pos = 0
    fun next(): Int {
        pos++
        return iterator.next()
    }

    fun readPacket(): Packet {
        val version = readVersion()
        return when (val type = readType()) {
            4 -> readLiteral(version)
            else -> readOperator(version, type)
        }
    }

    private fun readLiteral(version: Int): Literal {
        val sb = StringBuilder()
        while (true) {
            val part = readString(5)
            sb.append(part.substring(1))
            if (part.startsWith('0')) {
                break
            }
        }

        val value = sb.toString().let { BigInteger(it, 2).longValueExact() }

        return Literal(version, value)
    }

    private fun readOperator(version: Int, type: Int): Operator {
        val subPackets = when (readInt(1)) {
            0 -> readSubPacketsBits(readInt(15))
            1 -> readSubPacketsCount(readInt(11))
            else -> listOf()
        }
        return Operator(version, type, subPackets)
    }

    private fun readSubPacketsCount(count: Int) = (1..count).map { readPacket() }


    private fun readSubPacketsBits(bits: Int): List<Packet> {
        val start = pos
        return generateSequence { if (pos - start < bits) readPacket() else null }.toList()
    }

    private fun readVersion() = readInt(3)
    private fun readType() = readInt(3)
    private fun readInt(bits: Int) = (1..bits).fold(0) { acc, bit -> acc + (next() shl (bits - bit)) }
    private fun readString(bits: Int) = (1..bits).map { next() }.joinToString("")

    companion object {
        fun toBinary(char: Char) = Integer.toBinaryString(char.toString().toInt(16)).padStart(4, '0')
    }
}

fun d16p1(): Int = read("/day16.in").useLines { lines ->
    val readPacket = PacketDeserializer(lines.first()).readPacket()
    return readPacket.flatten().sumOf { it.version }
}

fun d16p2(): Long = read("/day16.in").useLines { lines ->
    val readPacket = PacketDeserializer(lines.first()).readPacket()
    return readPacket.calculate()
}

fun main() {
    println("Day 16")
    println(" ├─ ${d16p1()}")
    println(" └─ ${d16p2()}")
}