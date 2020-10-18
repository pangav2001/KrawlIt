import org.jsoup.Jsoup
import java.io.IOException

fun krawlURL(_URL: String, _links: HashSet<String>, _desNum: Int)
{
    if (!_links.contains(_URL) && _links.size < _desNum && _URL != "")
    {
        try
        {
            _links.add(_URL)
            val size = _links.size
            println("$size $_URL")

            val page = Jsoup.connect(_URL).get()

            val URLs = page.select("a[href]")

            for (html in URLs) krawlURL(html.attr("abs:href"), _links, _desNum)
        } catch (e: IOException)
        {
            val error = e.message
            System.err.println("Got: '$error' for '$_URL'")
        }
    }
}

fun main(args: Array<String>)
{
    println("Please enter the URL you want to crawl through: ")
    val URL: String = readLine()!!
    println("Please enter desired number of URLs: ")
    val desNum: Int = readLine()!!.toInt()
    val links: HashSet<String> = HashSet()
    krawlURL(URL, links, desNum)
}