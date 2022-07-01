package com.ecwid.util

import java.io.File

interface IpUnique {
    fun findFromFile(file: File): Pair<Int, Int>
}