package org.joaogsma.ports.web

import io.circe.parser.parse
import org.joaogsma.adapters.scryfall.CardAdapter
import org.joaogsma.entities.models.Card

import scala.util.Try

object ScryfallPort extends HttpPort {
  private val ROOT_URL: String = "https://api.scryfall.com/"
  private val SCRYFALL_REQUESTED_ELAPSED_TIME = 100

  def searchCardName(
      name: String,
      exact: Boolean = true,
      connectTimeout: Int = 5000,
      readTimeout: Int = 5000): Try[Card] = {
    val searchType = if (exact) "exact" else "fuzzy"
    val adjustedName = name.trim.replace(' ', '+')
    val url = s"$ROOT_URL/cards/named?$searchType=$adjustedName"

    val result: Try[String] = Try(get(url))

    val ts = System.currentTimeMillis()

    val card: Try[Card] = result
        .map(contentTry => parse(contentTry).toTry.get)
        .map(jsonTry => CardAdapter.jsonToCard(jsonTry).get)

    val elapsedMillis = System.currentTimeMillis() - ts
    if (elapsedMillis < SCRYFALL_REQUESTED_ELAPSED_TIME)
      Thread.sleep(100 - elapsedMillis)

    card
  }
}
