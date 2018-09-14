package org.joaogsma.adapters.scryfall

import io.circe.HCursor
import io.circe.Json
import org.joaogsma.models.Card

import scala.util.Try

object CardAdapter
{
  def jsonToCard(json: Json): Try[Card] =
  {
    val cursor: HCursor = json.hcursor

    val manaCost: Try[String] = cursor.get[String](MANA_COST_FIELD).toTry
    val cmc: Try[Double] = cursor.get[Double](CMC_FIELD).toTry
    val typeLine: Try[String] = cursor.get[String](TYPE_LINE_FIELD).toTry
    val colors: Try[Seq[String]] = cursor.get[Seq[String]](COLORS_FIELD).toTry

    Try(Card(
      ManaAdapter.parseSequence(manaCost.get),
      ColorAdapter.stringSeqToColorSeq(colors.get),
      TypeAdapter.parseSequence(typeLine.get),
      cmc.get
    ))
  }

  private val MANA_COST_FIELD: String = "mana_cost"
  private val CMC_FIELD: String = "cmc"
  private val TYPE_LINE_FIELD: String = "type_line"
  private val COLORS_FIELD: String = "colors"
}
