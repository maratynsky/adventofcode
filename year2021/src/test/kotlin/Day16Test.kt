import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16Test {

    @Test
    fun p1test() {
        assertEquals(16, PacketDeserializer("8A004A801A8002F478").readPacket().flatten().sumOf { it.version })
        assertEquals(12, PacketDeserializer("620080001611562C8802118E34").readPacket().flatten().sumOf { it.version })
        assertEquals(23, PacketDeserializer("C0015000016115A2E0802F182340").readPacket().flatten().sumOf { it.version })
        assertEquals(31, PacketDeserializer("A0016C880162017C3686B18A3D4780").readPacket().flatten().sumOf { it.version })
    }

    @Test
    fun p2test() {
        assertEquals(3, PacketDeserializer("C200B40A82").readPacket().calculate())
        assertEquals(54, PacketDeserializer("04005AC33890").readPacket().calculate())
        assertEquals(7, PacketDeserializer("880086C3E88112").readPacket().calculate())
        assertEquals(9, PacketDeserializer("CE00C43D881120").readPacket().calculate())
        assertEquals(1, PacketDeserializer("D8005AC2A8F0").readPacket().calculate())
        assertEquals(0, PacketDeserializer("F600BC2D8F").readPacket().calculate())
        assertEquals(0, PacketDeserializer("9C005AC2F8F0").readPacket().calculate())
        assertEquals(1, PacketDeserializer("9C0141080250320F1802104A08").readPacket().calculate())
    }
}