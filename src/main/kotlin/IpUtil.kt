import java.io.File
import java.util.*


class IpUtil {
    companion object {
        fun findUnique(file: File): Pair<Int, Int> {
            var duplicateIps = 0
            var unique = 0

            val ips0 = BitSet()
            val ips1 = BitSet()
            val ips2 = BitSet()
            val ips3 = BitSet()

            file.bufferedReader().forEachLine {
                val ipInt = ipToInt(it)
                var skip = false
                
                if(ips0.get(ipInt[0]) && ips1.get(ipInt[1]) && ips2[ipInt[2]] && ips3[ipInt[3]]) {
                    duplicateIps++
                    skip = true
                }

                if(!skip) {
                    ips0.set(ipInt[0])
                    ips1.set(ipInt[1])
                    ips2.set(ipInt[2])
                    ips3.set(ipInt[3])
                }
                
                unique++
            }

            return Pair(unique - duplicateIps, duplicateIps)
        }
        
        

        private fun ipToInt(ipString: String): IntArray {
            val ip = IntArray(4)
            val parts = ipString.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (i in 0..3) {
                ip[i] = parts[i].toInt()
            }
            
            return ip
        }
    }
}