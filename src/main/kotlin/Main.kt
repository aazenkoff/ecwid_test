import java.io.File
import java.util.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class Main
{
    fun generateIps(file: File, count: Int) {
        file.printWriter().use { out ->
            for (i in 0 until count step 1) {
                val ip: String = Random.nextInt(1, 255).toString() + "." + Random.nextInt(1, 255) + "." + Random.nextInt(1, 255) + "." + Random.nextInt(1, 254)
                out.println(ip)
            }
        }
    }
}

fun main() {
    val main = Main()
    val file = File("ip.txt")

    val reader = Scanner(System.`in`)
    
    with(reader) {
        if(hasNext()) {
            var command = next()

            if (command.equals("generate")) {
                print("enter count of ips: ")
                val count = nextInt()
                println("file is generating...")
                main.generateIps(file, count)
                println("file has been generated")
                command = next()
            }

            if (command.equals("find")) {
                val elapsed = measureTimeMillis {
                    val (unique, duplicate) = IpUtil.findUnique(file)
                    println("unique: $unique, duplicate: $duplicate")
                }

                println("time execution: $elapsed ms")
            }
        }
    }
}