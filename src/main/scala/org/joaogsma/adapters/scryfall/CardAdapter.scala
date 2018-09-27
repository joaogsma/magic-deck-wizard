package org.joaogsma.adapters.scryfall

import io.circe.Decoder
import io.circe.HCursor
import io.circe.Json
import org.joaogsma.models.Card

import scala.util.Try

object CardAdapter
{
  def jsonToCard(json: Json): Try[Card] =
  {
    val cursor: HCursor = json.hcursor

    val manaCost: Try[String] = getFieldFromCardOrFirstCardFace[String](cursor, MANA_COST_FIELD)
    val cmc: Try[Double] = getFieldFromCardOrFirstCardFace[Double](cursor, CMC_FIELD)
    val typeLine: Try[String] = getFieldFromCardOrFirstCardFace[String](cursor, TYPE_LINE_FIELD)
    val colors: Try[Seq[String]] = getFieldFromCardOrFirstCardFace[Seq[String]](cursor, COLORS_FIELD)

    Try(Card(
      ManaAdapter.parseToSequence(manaCost.get),
      ColorAdapter.parseToSequence(colors.get),
      TypeAdapter.parseToSequence(typeLine.get),
      cmc.get
    ))
  }

  def getFieldFromCardOrFirstCardFace[A : Decoder](cursor: HCursor, field: String): Try[A] =
  {
    cursor
        .get[A](field)
        .toTry
        .recoverWith
        {
          case _ => cursor.downField(CARD_FACES_FIELD).downArray.get[A](field).toTry
        }
  }

  private val MANA_COST_FIELD: String = "mana_cost"
  private val CMC_FIELD: String = "cmc"
  private val TYPE_LINE_FIELD: String = "type_line"
  private val COLORS_FIELD: String = "colors"
  private val CARD_FACES_FIELD: String = "card_faces"
}
