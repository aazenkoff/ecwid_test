package com.ecwid.util.impl

import com.ecwid.util.IpUnique
import java.io.File
import java.util.*


class IpUniqueImpl: IpUnique {
    
    private val ipSetMin = BitSet(Integer.MAX_VALUE)
    private val ipSetMax = BitSet(Integer.MAX_VALUE)
    
    override fun findFromFile(file: File): Pair<Int, Int> {
        var duplicateIps = 0
        var count = 0
        
        file.bufferedReader().forEachLine {
            val ipLong = ipToLong(it)

            when {
                getBit(ipLong) -> {
                    duplicateIps++
                } else -> {
                    setBit(ipLong)
                }
            }

            count++
        }
        
        val unique = (count - duplicateIps)

        return Pair(unique, duplicateIps)
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
            "Wrong IP address $ip in the file" 
        }
        return arrStr[0].toLong() * 256 * 256 * 256 + arrStr[1].toLong() * 256 * 256 + arrStr[2].toLong() * 256 + arrStr[3].toLong()
    }
}