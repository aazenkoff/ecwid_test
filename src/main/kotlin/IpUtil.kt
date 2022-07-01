import java.io.File
import java.util.*


class IpUtil {
    companion object {
        fun findUnique(file: File): Pair<Int, Int> {
            val bitIps = BitSet(Int.MAX_VALUE)
            var duplicateIps = 0
            var unique = 0

            val ips = mutableMapOf<Int, Map<Int, Map<Int, Int>>>()
            val ips2 = mutableMapOf<Int, Map<Int, Int>>()
            val ips3 = mutableMapOf<Int, Int>()

            file.bufferedReader().forEachLine {
                val ipInt = ipToInt(it)
                var skip = false
                
                if(ips3[ipInt[2]] == ipInt[3]) {
                    when {
                        ips2[ipInt[1]] == ips3 &&
                        ips[ipInt[0]] == ips2 -> {
                            duplicateIps++
                            skip = true
                        }
                    }
                }

                if(!skip) {
                    ips3[ipInt[2]] = ipInt[3]
                    ips2[ipInt[1]] = ips3
                    ips[ipInt[0]] = ips2
                }
                
                unique++
            }
            
//            println(ips)

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