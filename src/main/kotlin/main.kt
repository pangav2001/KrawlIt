import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.exitProcess

var links: ConcurrentHashMap <Int, String> = ConcurrentHashMap()
var desNum: Int = -1
var url: String = ""

fun CoroutineScope.krawURL(): ReceiveChannel<String> = produce{
        try
        {
            val page = Jsoup.connect(url).get()
            val urls = page.select("a[href]")
            for (html in urls) send(html.attr("abs:href"))
        }
        catch (e: IOException)
        {
            val error = e.message
            System.err.println("Got: '$error' for '$url'")
        }
}

fun main(args: Array<String>) = runBlocking{
    println("Please enter the URL you want to crawl through: ")
    url = readLine()!!
    println("Please enter desired number of URLs: ")
    desNum= readLine()!!.toInt()
    var size: Int = links.size
    val urls = krawURL()
    urls.consumeEach {
        if (!links.contains(url) && links.size < desNum && url != "")
        {
            links.put(it.hashCode(), it)
        }
        else
        {
            var counter = 1
            links.forEach{key, value -> println("$counter -> $value"); counter++}
            println("End of program")
            exitProcess(1)
        }
    }
    var counter = 1
    links.forEach{key, value -> println("$counter -> $value"); counter++}
    println("Only $counter URLs could be found\nEnd of program")
}

//+447708386951 for the O S I N T challenge in the C T F
