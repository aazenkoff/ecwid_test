import helper.ConsoleHelper
import util.IpUnique
import util.impl.IpUniqueImpl
import java.io.File
import java.lang.Thread.sleep
import java.util.*
import kotlin.system.measureTimeMillis

const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_RESET = "\u001B[0m"

class App {
    
    private var ipUnique: IpUnique = IpUniqueImpl()
    
    private var consoleHelper = ConsoleHelper()

    private fun execute(filename: String) {

        val file = File(filename)
        
        require(file.exists()) {
            "File not found"
        }
        
        println()
        
        val elapsed = measureTimeMillis {
            var processing = true
            
            val thread = Thread {
                while(processing) {
                    consoleHelper.animate("Processing file...")
                    sleep(500)
                }
            }
            thread.start()

            val (unique, duplicate) = ipUnique.findFromFile(file)
            sleep(10000)
            processing = false
            
            println()
            println()
            println("Unique IP: $ANSI_GREEN$unique$ANSI_RESET, Duplicated IP: $ANSI_GREEN$duplicate$ANSI_RESET")
        }

        println("Execution time: $ANSI_GREEN$elapsed ms$ANSI_RESET")
        println()
    }
    
    fun run() {
        val reader = Scanner(System.`in`)

        var endSearch = false
        
        while(!endSearch) {
            with(reader) {
                print("Enter the filename to find unique IP: ")
                val fileName = next()
                try {
                    execute(fileName)
                    endSearch = true
                } catch (ex: Exception) {
                    println(ANSI_RED + ex.message + ANSI_RESET)
                    println()
                }
            }
        }
    }
}