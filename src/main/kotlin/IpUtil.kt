import java.io.File
import java.util.*


class IpUtil {
    companion object {
        private val ipSetMin = BitSet(Integer.MAX_VALUE)
        private val ipSetMax = BitSet(Integer.MAX_VALUE)
        
        fun findUnique(file: File): Pair<Int, Int> {
            var duplicateIps = 0
            var unique = 0
            
            file.bufferedReader().forEachLine {
                val ipLong = ipToLong(it)
                var skip = false
                
                if(getBit(ipLong)) {
                    duplicateIps++
                    skip = true
                }
                
                if(!skip) {
                    setBit(ipLong)
                }
                
                unique++
            }

            return Pair(unique - duplicateIps, duplicateIps)
        }

        private fun getBit(ip: Long): Boolean {
            return if(ip > Integer.MAX_VALUE) {
                ipSetMax.get(ip.toInt() - Integer.MAX_VALUE)
            } else {
                ipSetMin.get(ip.toInt())
            }
        }
        
        private fun setBit(ip: Long) {
            if(ip > Integer.MAX_VALUE) {
                ipSetMax.set(ip.toInt() - Integer.MAX_VALUE)
            } else {
                ipSetMin.set(ip.toInt())
            }
        }


        private fun ipToLong(ip: String): Long {
            val arrStr = ip.split(".")
            require(arrStr.size == 4) { 
                "Wrong IP address $ip" 
            }
            return arrStr[0].toLong() * 256 * 256 * 256 + arrStr[1].toLong() * 256 * 256 + arrStr[2].toLong() * 256 + arrStr[3].toLong()
        }
    }
}