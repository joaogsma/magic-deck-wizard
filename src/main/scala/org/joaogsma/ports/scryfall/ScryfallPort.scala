package org.joaogsma.ports.scryfall

import org.joaogsma.ports.HttpPort
import io.circe.parser.parse
import org.joaogsma.adapters.scryfall.CardAdapter
import org.joaogsma.models.Card
import scala.util.Try

object ScryfallPort extends HttpPort
{
  private val ROOT_URL: String = "https://api.scryfall.com/"

  def searchCardName(
      name: String,
      exact: Boolean = true,
      connectTimeout: Int = 5000,
      readTimeout: Int = 5000): Try[Card] =
  {
    val searchType = if (exact) "exact" else "fuzzy"

    val url = s"$ROOT_URL/cards/named?$searchType=${name.replace(" ", "%20")}"

    Try(get(url))
        .map(contentTry => parse(contentTry).toTry.get)
        .map(jsonTry => CardAdapter.jsonToCard(jsonTry).get)
  }
}
