package util

import java.io.File

interface IpUnique {
    fun findFromFile(file: File): Pair<Int, Int>
}