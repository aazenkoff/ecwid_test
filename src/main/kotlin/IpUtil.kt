import java.io.File
import java.net.Inet4Address
import java.util.*


class IpUtil {
    companion object {
        fun findUnique(file: File): Pair<Int, Int> {
            val bitIps = BitSet(Int.MAX_VALUE)
            var duplicateIps = 0
            var unique = 0

            file.bufferedReader().forEachLine {
                var ipInt = ipToInt(it)
                if(ipInt < 0) {
                    ipInt *= -1
                }

                if (bitIps[ipInt]) {
                    duplicateIps++
                }
                bitIps[ipInt] = true
                unique++
            }

            return Pair(unique - duplicateIps, duplicateIps)
        }

        private fun ipToInt(ip: String): Int {
            val ipAddress = Inet4Address.getByName(ip)
            var result = 0
            for (b in ipAddress.address) {
                result = result shl 8 or (b.toInt() and 0xFF)
            }

            return result
        }
    }
}