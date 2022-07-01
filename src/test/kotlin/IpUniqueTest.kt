import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.ecwid.util.IpUnique
import com.ecwid.util.impl.IpUniqueImpl
import java.io.File
import kotlin.test.assertEquals

class IpUniqueTest {
    
    private val ipUnique: IpUnique = IpUniqueImpl()
    
    private lateinit var file:File
    
    private var ipList = listOf("145.67.23.4","8.34.5.23","89.54.3.124","89.54.3.124","3.45.71.5")
    
    @BeforeEach
    fun setUp() {
        file = File("ip_temp.txt")
        file.printWriter().use { out ->
            ipList.forEach {
                out.println(it)
            }
        }
    }
    
    @Test
    fun shouldReturnCorrectWhenFindUniqueIp() {
        val (unique, duplicated) = ipUnique.findFromFile(file)
        assertEquals(unique, 4)
        assertEquals(duplicated, 1)
    }
    
    @AfterEach
    fun after() {
        file.delete()
    }
}