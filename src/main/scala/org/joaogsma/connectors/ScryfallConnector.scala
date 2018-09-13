package org.joaogsma.connectors

object ScryfallConnector extends HttpConnector
{
  val ROOT_URL: String = "https://api.scryfall.com/"

  def searchCardName(
      name: String,
      exact: Boolean = true,
      connectTimeout: Int = 5000,
      readTimeout: Int = 5000): Option[String] =
  {
    val searchType = if (exact) "exact" else "fuzzy"

    val url = s"$ROOT_URL/cards/named?$searchType=${name.replace(" ", "%20")}"

    try
      Option(get(url))
    catch
    {
      case e: java.io.FileNotFoundException =>
        println(s"Could not find card named $name on url $url")
        Option.empty
      case e: java.net.UnknownHostException =>
        println("Could not connect to host")
        Option.empty
      case e: Throwable => throw e
    }
  }
}
