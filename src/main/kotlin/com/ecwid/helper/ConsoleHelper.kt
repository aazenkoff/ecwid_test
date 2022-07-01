package com.ecwid.helper

class ConsoleHelper {
    private var lastLine = ""
    
    private fun printOut(line: String) {
        if (lastLine.length > line.length) {
            var temp = ""
            for (i in lastLine.indices) {
                temp += " "
            }
            if (temp.length > 1) print("\r" + temp)
        }
        print("\r" + line)
        lastLine = line
    }

    private var anim: Int = 0
    
    fun animate(line: String) {
        when (anim) {
            1 -> printOut("[ \\ ] $line")
            2 -> printOut("[ | ] $line")
            3 -> printOut("[ / ] $line")
            else -> {
                anim = 0
                printOut("[ - ] $line")
            }
        }
        anim++
    }
}