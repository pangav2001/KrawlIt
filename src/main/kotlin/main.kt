import org.jsoup.Jsoup
import java.io.IOException

fun krawlURL(url: String, links: HashSet<String>, desNum: Int)
{
    if (!links.contains(url) && links.size < desNum && url != "")
    {
        try
        {
            links.add(url)
            val size = links.size
            println("$size $url")
            val page = Jsoup.connect(url).get()
            val urls = page.select("a[href]")
            for (html in urls) krawlURL(html.attr("abs:href"), links, desNum)
        }
        catch (e: IOException)
        {
            val error = e.message
            System.err.println("Got: '$error' for '$url'")
        }
    }
}

fun main(args: Array<String>)
{
    println("Please enter the URL you want to crawl through: ")
    val url: String = readLine()!!
    println("Please enter desired number of URLs: ")
    val desNum: Int = readLine()!!.toInt()
    val links: HashSet<String> = HashSet()
    krawlURL(url, links, desNum)
}