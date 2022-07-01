import java.io.File
import java.nio.ByteBuffer
import java.util.*


class IpUtil {
    companion object {
        fun findUnique(file: File): Pair<Int, Int> {
            val positive = BitSet(Int.MAX_VALUE)
            val negative = BitSet(Int.MAX_VALUE)
            var duplicateIps = 0
            var unique = 0

            file.bufferedReader().forEachLine {
                val ipInt = ipToInt(it)
                val ipIntNegative = ipInt * (-1)

                if(ipInt < 0) {
                    if (negative[ipIntNegative]) {
                        duplicateIps++
                    }
                    negative[ipIntNegative] = true
                } else {
                    if (positive[ipInt]) {
                        duplicateIps++
                    }
                    positive[ipInt] = true
                }
                unique++
            }

            return Pair(unique - duplicateIps, duplicateIps)
        }

        private fun ipToInt(ip: String): Int {
            return ByteBuffer.wrap(textToNumericFormatV4(ip)).int;
        }

        private fun textToNumericFormatV4(src: String): ByteArray? {
            val res = ByteArray(4)
            var tmpValue: Long = 0
            var currByte = 0
            var newOctet = true
            val len = src.length
            if (len == 0 || len > 15) {
                return null
            }
            for (i in 0 until len) {
                val c = src[i]
                if (c == '.') {
                    if (newOctet || tmpValue < 0 || tmpValue > 0xff || currByte == 3) {
                        return null
                    }
                    res[currByte++] = (tmpValue and 0xffL).toByte()
                    tmpValue = 0
                    newOctet = true
                } else {
                    val digit = c.digitToIntOrNull() ?: -1
                    if (digit < 0) {
                        return null
                    }
                    tmpValue *= 10
                    tmpValue += digit.toLong()
                    newOctet = false
                }
            }
            if (newOctet || tmpValue < 0 || tmpValue >= 1L shl 4 - currByte * 8) {
                return null
            }
            when (currByte) {
                0 -> {
                    res[0] = (tmpValue shr 24 and 0xffL).toByte()
                    res[1] = (tmpValue shr 16 and 0xffL).toByte()
                    res[2] = (tmpValue shr 8 and 0xffL).toByte()
                    res[3] = (tmpValue shr 0 and 0xffL).toByte()
                }
                1 -> {
                    res[1] = (tmpValue shr 16 and 0xffL).toByte()
                    res[2] = (tmpValue shr 8 and 0xffL).toByte()
                    res[3] = (tmpValue shr 0 and 0xffL).toByte()
                }
                2 -> {
                    res[2] = (tmpValue shr 8 and 0xffL).toByte()
                    res[3] = (tmpValue shr 0 and 0xffL).toByte()
                }
                3 -> res[3] = (tmpValue shr 0 and 0xffL).toByte()
            }
            return res
        }
    }
}